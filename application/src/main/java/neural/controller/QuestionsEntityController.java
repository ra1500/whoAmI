package neural.controller;

// import .Paths;     --use later if wish to have Paths restricted/opened via separate class--
import db.entity.QuestionSetVersionEntity;
import db.repository.QuestionSetVersionRepositoryDAO;
import db.repository.QuestionsRepositoryDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.QuestionsEntityDto;
import core.services.QuestionsEntityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping(value = "q", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "QuestionsEntity endpoints", tags = "QuestionsEntity")
public class QuestionsEntityController extends AbstractRestController {

    private QuestionsEntityService questionsEntityService;

    // use in micro-service in GET
    private QuestionSetVersionRepositoryDAO questionSetVersionRepositoryDAO;

    public QuestionsEntityController(QuestionsEntityService questionsEntityService, QuestionSetVersionRepositoryDAO questionSetVersionRepositoryDAO) {
        this.questionsEntityService = questionsEntityService;
        this.questionSetVersionRepositoryDAO = questionSetVersionRepositoryDAO; }

    // GET a single question.
    @ApiOperation(value = "getQuestionsEntity")
    @RequestMapping(value = "/{qsid}/{qid}", method = RequestMethod.GET)
    public ResponseEntity<QuestionsEntityDto> getQuestionsEntity(
            @RequestHeader("Authorization") String token,
            @PathVariable("qsid")
            final Long questionSetVersionEntityId,
            @PathVariable("qid")
            final Long sequenceNumber) {

        // secured by token
        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        QuestionsEntityDto questionsEntityDto = questionsEntityService.getQuestionsEntity(sequenceNumber, questionSetVersionEntityId);

        if (questionsEntityDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(questionsEntityDto);
    }
    // POST/PATCH  posts a new one, updates an existing one
    @RequestMapping(value = "/p{qsid}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionsEntityDto> createQuestionsEntity(
            @Valid
            @RequestBody final QuestionsEntityDto questionsEntityDto,
            @RequestHeader("Authorization") String token,
            @RequestParam("qsid")
            final Long questionSetVersionEntityId) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        questionsEntityDto.setCreativeSource(user);

        QuestionsEntityDto savedQuestionsEntityDto = questionsEntityService.createQuestionsEntity(questionsEntityDto, questionSetVersionEntityId);
        return ResponseEntity.ok(savedQuestionsEntityDto);
    }
    
}