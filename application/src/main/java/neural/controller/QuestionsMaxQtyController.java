package neural.controller;

// import .Paths;     --use later if wish to have Paths restricted/opened via separate class--
import db.repository.QuestionsRepositoryDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "max", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "User Scores endpoints", tags = "UserScoresAggregates")
public class QuestionsMaxQtyController extends AbstractRestController {

    private QuestionsRepositoryDAO questionsRepositoryDAO;
    private String maxQtyQuestionsJSON;

    public QuestionsMaxQtyController(QuestionsRepositoryDAO questionsRepositoryDAO) {
        this.questionsRepositoryDAO = questionsRepositoryDAO; }

    // any requester with a token can access.
    @ApiOperation(value = "getMaxQtyQuestions")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<String> getMaxQtyQuestions() {
        Long maxQtyQuestions = questionsRepositoryDAO.findMaxQtyQuestions();
        if (maxQtyQuestions == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
        else {
            String maxQtyQuestionsJSON = "{\"maxQtyQuestions\":" + maxQtyQuestions + "}";
            return ResponseEntity.ok(maxQtyQuestionsJSON);}
    }
}

