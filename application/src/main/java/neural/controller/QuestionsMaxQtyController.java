package neural.controller;

// import .Paths;     --use later if wish to have Paths restricted/opened via separate class--
import db.repository.QuestionsRepositoryDAO;
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
@RequestMapping(value = "max", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "User Scores endpoints", tags = "UserScoresAggregates")
public class QuestionsMaxQtyController extends AbstractRestController {

    private QuestionsRepositoryDAO questionsRepositoryDAO;
    private String maxQtyQuestionsJSON;

    public QuestionsMaxQtyController(QuestionsRepositoryDAO questionsRepositoryDAO) {
        this.questionsRepositoryDAO = questionsRepositoryDAO; }

    // GET qty of questions
    @ApiOperation(value = "getMaxQtyQuestions")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<String> getMaxQtyQuestions(
        @RequestHeader("Authorization") String token,
        @RequestParam("sn") final Integer setNumber) {

        // secured by token
        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        Integer maxQtyQuestions = questionsRepositoryDAO.findMaxQtyQuestions(setNumber);
        if (maxQtyQuestions == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
        else {
            String maxQtyQuestionsJSON = "{\"maxQtyQuestions\":" + maxQtyQuestions + "}";
            return ResponseEntity.ok(maxQtyQuestionsJSON);}
    }

    // GET maxPoints of a set of questions
    //@ApiOperation(value = "getMaxQtyQuestions")
    //@RequestMapping(value = "", method = RequestMethod.GET)
    //public ResponseEntity<String> getMaxPoints(
    //        @RequestHeader("Authorization") String token,
    //        @RequestParam("snp") final Integer setNumber) {

        // secured by token
    //    String base64Credentials = token.substring("Basic".length()).trim();
    //    byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
    //    String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
    //    final String[] values = credentials.split(":", 2);
    //    String user = values[0];

     //   Long maxPoints = questionsRepositoryDAO.MaxPointsForSetNumber(setNumber);
     //   if (maxPoints == null) {
     //       return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
     //   else {
     //       String maxQtyQuestionsJSON = "{\"maxPoints\":" + maxPoints + "}";
     //       return ResponseEntity.ok(maxQtyQuestionsJSON);}
    //}
}

