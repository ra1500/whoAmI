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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

    // get an single question. any user with a token can access.
    @ApiOperation(value = "getQuestionsEntity")
    @RequestMapping(value = "/{qsid}/{qid}", method = RequestMethod.GET)
    public ResponseEntity<QuestionsEntityDto> getQuestionsEntity(
            @PathVariable("qsid")
            final Integer setNumber,

            @PathVariable("qid")
            final Integer sequenceNumber) {

        // QuestionSetVersionEntity service.
        QuestionSetVersionEntity questionSetVersionEntity = questionSetVersionRepositoryDAO.findOneBySetNumber(setNumber);

        QuestionsEntityDto questionsEntityDto = questionsEntityService.getQuestionsEntity(questionSetVersionEntity, sequenceNumber);

        if (questionsEntityDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(questionsEntityDto);
    }

//    @ApiOperation(value = "getQuestionsQuantity")
//    @RequestMapping(value = "/qty", method = RequestMethod.GET)
//    public ResponseEntity<Long> getQuestionsQuantity() {
//
//        Long qty;
//        qty = QuestionsRepositoryDAO.findMaxGid();
//
//        if (qty == null) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//
//        return ResponseEntity.ok(qty);
//    }
    
}