package neural.controller;

// import Paths;     --use later if wish to have Paths restricted/opened via separate class--
import core.services.UserAnswersEntityService;
import db.entity.UserAnswersEntity;
import db.repository.UserAnswersRepositoryDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.UserAnswersEntityDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;

@RestController
@RequestMapping(value = "a", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "UserAnswersEntity endpoints", tags = "UserAnswersEntity")
public class UserAnswersEntityController extends AbstractRestController {

    private UserAnswersEntityService userAnswersEntityService;
    private UserAnswersRepositoryDAO userAnswersRepositoryDAO; // used for delete. shortcut to the repository.

    public UserAnswersEntityController(UserAnswersEntityService userAnswersEntityService, UserAnswersRepositoryDAO userAnswersRepositoryDAO) {
        this.userAnswersEntityService = userAnswersEntityService;
        this.userAnswersRepositoryDAO = userAnswersRepositoryDAO;
    }

    // GET a user's answer to a question
    @ApiOperation(value = "getUserAnswersEntity")
    @RequestMapping(value = "/{qId}/{au}", method = RequestMethod.GET)
    public ResponseEntity<UserAnswersEntityDto> getUserAnswersEntity(
            @RequestHeader("Authorization") String token,
            @PathVariable("qId") final Long questionsEntityId,
            @PathVariable("au") final String auditee) {
        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        UserAnswersEntityDto userAnswersEntityDto = userAnswersEntityService.getUserAnswersEntity(user, auditee, questionsEntityId );
        if (userAnswersEntityDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(userAnswersEntityDto);
    }

    // POST/PATCH a user's answer to a question.
    @RequestMapping(value = "/{qId}",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAnswersEntityDto> createUserAnswersEntity(
            @Valid
            @RequestBody
            final UserAnswersEntityDto userAnswersEntityDto,
            @RequestHeader("Authorization") String token,
            @PathVariable("qId") final Long questionsEntityId) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        // userName from token
        userAnswersEntityDto.setUserName(user);

        UserAnswersEntityDto savedUserAnswersEntityDto = userAnswersEntityService.createUserAnswersEntity(userAnswersEntityDto, questionsEntityId);
        return ResponseEntity.ok(savedUserAnswersEntityDto);
    }

    // POST delete all of a user's answers
    @RequestMapping(value = "/del/{qsId}",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAllUserAnswers(
            @Valid
            @RequestBody
            final UserAnswersEntityDto userAnswersEntityDto,
            @RequestHeader("Authorization") String token,
            @PathVariable("qsId") final Long questionSetVersionEntityId) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        String deleted = userAnswersEntityService.deleteAllAnswersForUserNameAndAuditeeAndQuestionSetVersionEntityId(user, userAnswersEntityDto.getAuditee(), questionSetVersionEntityId);

        return ResponseEntity.ok(deleted);
    }

    //  -- Aggregate Queries --
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // GET. Private Profile Page.
    @ApiOperation(value = "getUserScoresAggregates")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getUserScoresAggregate(
            @RequestHeader("Authorization") String token,
            @RequestParam("sv") final Long questionSetVersionEntityId,
            @RequestParam("au") final String auditee) {
        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        Long userScore = userAnswersRepositoryDAO.findUserScoresTotal(user, auditee, questionSetVersionEntityId);

        if (userScore == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
        else {
            String userScoreJSON = "{\"userScore\":" + userScore + "}";
            return ResponseEntity.ok(userScoreJSON);}
    }

    // GET for use in the public URL. anyone can access.
    @ApiOperation(value = "getUserScoresAggregates")
    @RequestMapping(value = "/scores", method = RequestMethod.GET)
    public ResponseEntity<String> getUserScore(
            @RequestParam("id") final String userName,
            @RequestParam("sv") final Long questionSetVersion) {

        // Checking Permissions
        //UserEntity userEntity = findOneByUser

        String auditee = userName;
        Long userScore = userAnswersRepositoryDAO.findUserScoresTotal(userName, auditee, questionSetVersion);

        if (userScore > 0) {
            String userScoreJSON = "{\"userScore\":" + userScore + "}";
            return ResponseEntity.ok(userScoreJSON);}
        // this else does not work. can't evaluate if expression if SQL finds nothing
        else {
            String userScoreJSON = "{\"userScore\": 0 }";
            return ResponseEntity.ok(userScoreJSON);}
    }

    // GET Qsets and Results for the privateProfilePage.
    @ApiOperation(value = "getPrivateProfileQsets")
    @RequestMapping(value = "/pr", method = RequestMethod.GET)
    public ResponseEntity<Set<UserAnswersEntity>>getPrivateProfileQsets(
            @RequestHeader("Authorization") String token) {

        // secured by token
        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        Set<UserAnswersEntity> userAnswersEntities = userAnswersRepositoryDAO.findSome();


        if (userAnswersEntities == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(userAnswersEntities);
    }

}
