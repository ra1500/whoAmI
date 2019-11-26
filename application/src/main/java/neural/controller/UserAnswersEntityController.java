package neural.controller;

// import Paths;     --use later if wish to have Paths restricted/opened via separate class--
import core.services.UserAnswersEntityService;
import db.entity.UserAnswersEntity;
import db.repository.FriendshipsRepositoryDAO;
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
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "a", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "UserAnswersEntity endpoints", tags = "UserAnswersEntity")
public class UserAnswersEntityController extends AbstractRestController {

    private UserAnswersEntityService userAnswersEntityService;
    private UserAnswersRepositoryDAO userAnswersRepositoryDAO; // used for delete. shortcut to the repository.
    private FriendshipsRepositoryDAO friendshipsRepositoryDAO; // used in GET to pull an answer for use in NetworkContactAudit

    public UserAnswersEntityController(UserAnswersEntityService userAnswersEntityService, UserAnswersRepositoryDAO userAnswersRepositoryDAO,
                                       FriendshipsRepositoryDAO friendshipsRepositoryDAO) {
        this.userAnswersEntityService = userAnswersEntityService;
        this.userAnswersRepositoryDAO = userAnswersRepositoryDAO;
        this.friendshipsRepositoryDAO = friendshipsRepositoryDAO;
    }

    // GET a user's answer to a question. self self. for 'Questions'.
    @ApiOperation(value = "getUserAnswersEntity")
    @RequestMapping(value = "/l/{qId}", method = RequestMethod.GET)
    public ResponseEntity<UserAnswersEntityDto> getUserAnswersEntity(
            @RequestHeader("Authorization") String token,
            @PathVariable("qId") final Long questionsEntityId) {
        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        UserAnswersEntityDto userAnswersEntityDto = userAnswersEntityService.getUserAnswersEntity(user, user, questionsEntityId );
        if (userAnswersEntityDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(userAnswersEntityDto);
    }

    // GET a user's answer to a question. self auditee. for 'AuditQuestions'.
    @ApiOperation(value = "getUserAnswersEntityDuringAudit")
    @RequestMapping(value = "/b/{qId}/{fId}", method = RequestMethod.GET)
    public ResponseEntity<UserAnswersEntityDto> getUserAnswersEntityDuringAudit(
            @RequestHeader("Authorization") String token,
            @PathVariable("qId") final Long questionsEntityId,
            @PathVariable("fId") final Long friendshipsEntityId) {
        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        String auditee = friendshipsRepositoryDAO.findOneById(friendshipsEntityId).getFriend();

        UserAnswersEntityDto userAnswersEntityDto = userAnswersEntityService.getUserAnswersEntity(user, auditee, questionsEntityId );
        if (userAnswersEntityDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(userAnswersEntityDto);
    }

    // GET an answer in order to load the qset. For use in 'NetworkContactAudit'
    @ApiOperation(value = "getUserAnswersEntity")
    @RequestMapping(value = "/au/{friendId}", method = RequestMethod.GET)
    public ResponseEntity<UserAnswersEntityDto> getUserAnswersEntityAudit(
            @RequestHeader("Authorization") String token,
            @PathVariable("friendId") final Long friendId) {
        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        String friend = friendshipsRepositoryDAO.findOneById(friendId).getFriend();

        UserAnswersEntityDto userAnswersEntityDto = userAnswersEntityService.getUserAnswersEntity(user, friend);
        if (userAnswersEntityDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(userAnswersEntityDto);
    }

    // GET userScore Total for 'Questions'. (userName & auditee same).
    @ApiOperation(value = "getUserScoresAggregates")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getUserScoresAggregate(
            @RequestHeader("Authorization") String token,
            @RequestParam("sv") final Long questionSetVersionEntityId) {
        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        Long userScore = userAnswersRepositoryDAO.findUserScoresTotal(user, user, questionSetVersionEntityId);

        if (userScore == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            String userScoreJSON = "{\"userScore\":" + userScore + "}";
            return ResponseEntity.ok(userScoreJSON);
        }
    }

    // GET userScore Total for 'AuditQuestions'. (userName & auditee different).
    @ApiOperation(value = "getUserScoresAggregatesDuringAudit")
    @RequestMapping(value = "/z{sv}{fId}", method = RequestMethod.GET)
    public ResponseEntity<String> getUserScoresAggregateDuringAudit(
            @RequestHeader("Authorization") String token,
            @RequestParam("sv") final Long questionSetVersionEntityId,
            @RequestParam("fId") final Long friendId) {
        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        String friend = friendshipsRepositoryDAO.findOneById(friendId).getFriend();
        Long userScore = userAnswersRepositoryDAO.findUserScoresTotal(user, friend, questionSetVersionEntityId);

        if (userScore == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            String userScoreJSON = "{\"userScore\":" + userScore + "}";
            return ResponseEntity.ok(userScoreJSON);
        }
    }

    // GET list of audit answers. used in 'ViewAudits'
    @ApiOperation(value = "ViewAudits list")
    @RequestMapping(value = "/y{sv}{fnm}", method = RequestMethod.GET)
    public ResponseEntity<List<UserAnswersEntity>> getAuditorsAnswersComments(
            @RequestHeader("Authorization") String token,
            @RequestParam("sv") final Long questionSetVersionEntityId,
            @RequestParam("fnm") final String friend) {
        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        List<UserAnswersEntity> data = userAnswersRepositoryDAO.findAuditDetails(friend, user);

        if (data == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            //String userScoreJSON = "{\"userScore\":" + userScore + "}";
            return ResponseEntity.ok(data);
        }
    }

    // POST/PATCH a user's answer to a question.
    @RequestMapping(value = "/r/{qId}",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
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

    // POST audit permissions for a qset. from 'ScoresList'/manageAudits.
    @RequestMapping(value = "/fr/{qsId}/{grp}",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> allowAuditsFriends(
            @Valid
            @RequestBody
            final UserAnswersEntityDto userAnswersEntityDto,
            @RequestHeader("Authorization") String token,
            @PathVariable("qsId") final Long questionSetVersionEntityId,
            @PathVariable("grp") String group) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        if (group.equals("e")) {
            String auditorsSet = userAnswersEntityService.createUserAnswersEntitiesForAuditsEveryone(user, questionSetVersionEntityId);
        };

        if (group.equals("f")) { group = "Friend"; };
        if (group.equals("c")) { group = "Colleague"; };
        if (group.equals("o")) { group = "Other"; };
        String auditorsSet = userAnswersEntityService.createUserAnswersEntitiesForAudits(user, questionSetVersionEntityId, group);

        return ResponseEntity.ok(auditorsSet);
    }

    // POST audit permissions for a qset (Individual). from 'ScoresList'/manageAudits.
    @RequestMapping(value = "/in/{qsId}",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> allowAuditsIndividual(
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

        String auditorsSet = userAnswersEntityService.createUserAnswersEntitiesForAuditsIndividual(user, userAnswersEntityDto.getUserName(), questionSetVersionEntityId);

        return ResponseEntity.ok(auditorsSet);
    }


}
