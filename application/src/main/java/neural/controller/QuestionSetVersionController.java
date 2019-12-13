package neural.controller;

import core.services.QuestionSetVersionEntityService;
import core.services.QuestionsEntityService;
import db.entity.QuestionSetVersionEntity;
import db.repository.PermissionsRepositoryDAO;
import db.repository.QuestionSetVersionRepositoryDAO;
import db.repository.QuestionsRepositoryDAO;
import db.repository.UserAnswersRepositoryDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.QuestionSetVersionEntityDto;
import model.QuestionsEntityDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping(value = "qs", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "QuestionsEntity endpoints", tags = "QuestionsEntity")
public class QuestionSetVersionController extends AbstractRestController {

    private QuestionSetVersionEntityService questionSetVersionEntityService;
    private QuestionsRepositoryDAO questionsRepositoryDAO;
    private QuestionSetVersionRepositoryDAO questionSetVersionRepositoryDAO;
    private PermissionsRepositoryDAO permissionsRepositoryDAO;
    private UserAnswersRepositoryDAO userAnswersRepositoryDAO;

    public QuestionSetVersionController(QuestionSetVersionEntityService questionSetVersionEntityService,
                                        QuestionsRepositoryDAO questionsRepositoryDAO, QuestionSetVersionRepositoryDAO questionSetVersionRepositoryDAO, PermissionsRepositoryDAO permissionsRepositoryDAO, UserAnswersRepositoryDAO userAnswersRepositoryDAO) {
        this.questionSetVersionEntityService = questionSetVersionEntityService;
        this.questionsRepositoryDAO = questionsRepositoryDAO;
        this.questionSetVersionRepositoryDAO = questionSetVersionRepositoryDAO;
        this.permissionsRepositoryDAO = permissionsRepositoryDAO;
        this.userAnswersRepositoryDAO = userAnswersRepositoryDAO; }

    // GET questionSetVersion. DTO excludes Set<Questions> to reduce load.
    @ApiOperation(value = "getQuestionsEntity")
    @RequestMapping(value = "/g{qsid}", method = RequestMethod.GET)
    public ResponseEntity<QuestionSetVersionEntityDto> getQuestionSetVersionEntity(
            @RequestHeader("Authorization") String token,
            @RequestParam("qsid") final Long qsid) {

        // secured by token
        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        QuestionSetVersionEntityDto questionSetVersionEntityDto = questionSetVersionEntityService.getQuestionSetVersionEntity(qsid);

        if (questionSetVersionEntityDto == null) { return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
        return ResponseEntity.ok(questionSetVersionEntityDto);
    }

    // POST/PATCH  posts a new one, updates an existing one
    @RequestMapping(value = "/p{qsid}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionSetVersionEntityDto> createQuestionSetVersionEntity(
            @Valid
            @RequestBody final QuestionSetVersionEntityDto questionSetVersionEntityDto,
            @RequestHeader("Authorization") String token,
            @RequestParam("qsid") final Long qsid) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        // userName from token
        questionSetVersionEntityDto.setCreativeSource(user);

        // limit quantity to 10 qsets per account
        Long countOfQsets = questionSetVersionRepositoryDAO.countQuantityQsetsByUsername(user);
        if (countOfQsets > 9) { return new ResponseEntity<>(HttpStatus.NO_CONTENT); };

        QuestionSetVersionEntityDto savedQuestionSetVersionEntityDto = questionSetVersionEntityService.createQuestionSetVersionEntity(questionSetVersionEntityDto, qsid, user);
        return ResponseEntity.ok(savedQuestionSetVersionEntityDto);
    }

    // GET Scores page. maxPoints and maxQtyQuestions
    @ApiOperation(value = "getMaxQtyQuestions")
    @RequestMapping(value = "/q{sn}", method = RequestMethod.GET)
    public ResponseEntity<String> getMaxQtyQuestions(
            @RequestHeader("Authorization") String token,
            @RequestParam("sn") final Long questionSetVersion) {

        // secured by token
        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        Long maxQtyQuestions = questionsRepositoryDAO.findMaxQtyQuestions(questionSetVersion);
        Long maxPoints = questionsRepositoryDAO.PointsForQuestionSetVersion(questionSetVersion);

        if (maxQtyQuestions == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
        else {
            String maxQtyQuestionsJSON = "{\"maxQtyQuestions\":" + maxQtyQuestions + ", \"maxPoints\":" + maxPoints + "}";
            return ResponseEntity.ok(maxQtyQuestionsJSON);}
    }

    // POST  delete Qset (and all related questions, permissions and answers).
    @RequestMapping(value = "/da", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAllQuestionsInQset(
            @Valid
            @RequestBody final QuestionSetVersionEntityDto questionSetVersionEntityDto,
            @RequestHeader("Authorization") String token) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        // validate user is creator
        QuestionSetVersionEntity foundQuestionSetVersionEntity = questionSetVersionRepositoryDAO.findOneById(questionSetVersionEntityDto.getId());
        if (!foundQuestionSetVersionEntity.getCreativeSource().equals(user)) {
            String noCredentials = "no valid token";
            return ResponseEntity.ok(noCredentials);
        }

        permissionsRepositoryDAO.deleteAllByQuestionSetVersionEntityId(questionSetVersionEntityDto.getId());
        userAnswersRepositoryDAO.deleteAllByQuestionSetVersionEntityId(questionSetVersionEntityDto.getId());
        questionsRepositoryDAO.deleteAllByQuestionSetVersionEntityId(questionSetVersionEntityDto.getId());
        questionSetVersionRepositoryDAO.deleteById(questionSetVersionEntityDto.getId());

        String allDeleted = "Deleted";

        return ResponseEntity.ok(allDeleted);
    }

}
