package neural.controller;

// import .Paths;     --use later if wish to have Paths restricted/opened via separate class--
import db.repository.UserAnswersRepositoryDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "us", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "User Scores endpoints", tags = "UserScoresAggregates")
public class UserScoresAggregatesController extends AbstractRestController {

    private UserAnswersRepositoryDAO userAnswersRepositoryDAO;
    private String UserScoreJSON;

    public UserScoresAggregatesController(UserAnswersRepositoryDAO userAnswersRepositoryDAO) {
        this.userAnswersRepositoryDAO = userAnswersRepositoryDAO; }

    @ApiOperation(value = "getUserScoresAggregates")
    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    public ResponseEntity<String> getUserScoresAggregate(
            @PathVariable("userName") final String userName) {
        Long userScore = userAnswersRepositoryDAO.findUserScoresTotal(userName);
        if (userScore == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
        else {
            String userScoreJSON = "{\"userScore\":" + userScore + "}";
        return ResponseEntity.ok(userScoreJSON);}
    }
    @ApiOperation(value = "getUserScoresAggregates")
    @RequestMapping(value = "/scores", method = RequestMethod.GET)
    public ResponseEntity<String> getUserScore(
            @RequestParam("gid") final String userName) {
        Long userScore = userAnswersRepositoryDAO.findUserScoresTotal(userName);
        if (userScore == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
        else {
            String userScoreJSON = "{\"userScore\":" + userScore + "}";
            return ResponseEntity.ok(userScoreJSON);}
    }

}
