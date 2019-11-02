package neural.controller;

// import .Paths;     --use later if wish to have Paths restricted/opened via separate class--
import db.entity.UserEntity;
import db.repository.UserAnswersRepositoryDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping(value = "us", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "User Scores endpoints", tags = "UserScoresAggregates")
public class UserScoresAggregatesController extends AbstractRestController {

    private UserAnswersRepositoryDAO userAnswersRepositoryDAO;
    private String UserScoreJSON;

    public UserScoresAggregatesController(UserAnswersRepositoryDAO userAnswersRepositoryDAO) {
        this.userAnswersRepositoryDAO = userAnswersRepositoryDAO; }

    // GET. Private Profile Page.
    @ApiOperation(value = "getUserScoresAggregates")
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getUserScoresAggregate(
            @RequestHeader("Authorization") String token,
            @RequestParam("sv") final Long questionSetVersion,
            @RequestParam("au") final String auditee) {
        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        Long userScore = userAnswersRepositoryDAO.findUserScoresTotal(user, auditee, questionSetVersion);
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
            @RequestParam("gid") final String userName,
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
}
