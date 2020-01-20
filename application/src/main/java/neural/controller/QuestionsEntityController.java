package neural.controller;

// import .Paths;     --use later if wish to have Paths restricted/opened via separate class--
import db.entity.QuestionSetVersionEntity;
import db.entity.QuestionsEntity;
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
import java.util.*;

@RestController
@RequestMapping(value = "/api/q", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "QuestionsEntity endpoints", tags = "QuestionsEntity")
public class QuestionsEntityController extends AbstractRestController {

    private QuestionsEntityService questionsEntityService;
    private QuestionsRepositoryDAO questionsRepositoryDAO;

    // use in micro-service in GET and Delete
    private QuestionSetVersionRepositoryDAO questionSetVersionRepositoryDAO;

    public QuestionsEntityController(QuestionsEntityService questionsEntityService, QuestionSetVersionRepositoryDAO questionSetVersionRepositoryDAO,
                                     QuestionsRepositoryDAO questionsRepositoryDAO) {
        this.questionsEntityService = questionsEntityService;
        this.questionSetVersionRepositoryDAO = questionSetVersionRepositoryDAO;
        this.questionsRepositoryDAO = questionsRepositoryDAO; }

    // GET. Lazy load a single question. without parent (Lazy load)
    @ApiOperation(value = "getQuestionsEntity")
    @RequestMapping(value = "/e/{qsid}/{qid}", method = RequestMethod.GET)
    public ResponseEntity<QuestionsEntityDto> getQuestionsEntity(
            @RequestHeader("Authorization") String token,
            @PathVariable("qsid")
            final Long questionSetVersionEntityId,
            @PathVariable("qid")
            final Long sequenceNumber) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        QuestionsEntityDto questionsEntityDto = questionsEntityService.getQuestionsEntity(sequenceNumber, questionSetVersionEntityId);

        if (questionsEntityDto == null) { return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
        return ResponseEntity.ok(questionsEntityDto);
    }

    // GET. Eager load a single question. with Parent. For 'AskManage'
    @ApiOperation(value = "getQuestionsEntity")
    @RequestMapping(value = "/f/{qsId}", method = RequestMethod.GET)
    public ResponseEntity<QuestionsEntityDto> getQuestionsEntityWithParent(
            @RequestHeader("Authorization") String token,
            @PathVariable("qsId")
            final Long questionSetVersionEntityId) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        QuestionsEntityDto questionsEntityDto = questionsEntityService.getQuestionsEntityWithParent(questionSetVersionEntityId);

        if (questionsEntityDto == null) { return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
        return ResponseEntity.ok(questionsEntityDto);
    }

    // GET. Set of questions with correct answers for a questionSetVersion.
    @ApiOperation(value = "getQuestionsEntity")
    @RequestMapping(value = "/b{qsId}", method = RequestMethod.GET)
    public ResponseEntity<List<QuestionsEntity>> getQuestionsEntitiesCorrectAnswers(
            @RequestHeader("Authorization") String token,
            @RequestParam("qsId") final Long questionSetVersionEntityId) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        // TODO: put this in service class. Maybe a better solution? Add a field to QuestionsEntity for 'correct answer'?
        Set<QuestionsEntity> foundQuestionsEntities = questionsRepositoryDAO.findStuff(questionSetVersionEntityId);

        if (foundQuestionsEntities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // make the correct answer be in 'slot' 'Answer1' and then lighten the DTO load. Will render in front-end showing only sequence# and Answer1 and Answer1Points.
        for (QuestionsEntity x : foundQuestionsEntities) {
            Long max = Math.max(Math.max(Math.max(Math.max(Math.max(x.getAnswer1Points(), x.getAnswer2Points()), x.getAnswer3Points()), x.getAnswer4Points()), x.getAnswer5Points()), x.getAnswer6Points() );
            if      (x.getAnswer1Points().equals(max)) {x.setAnswer1(x.getAnswer1()); x.setAnswer1Points(max); x.setAnswer2(null); x.setAnswer2Points(null); x.setAnswer3(null); x.setAnswer3Points(null); x.setAnswer4(null); x.setAnswer4Points(null); x.setAnswer5(null); x.setAnswer5Points(null); x.setAnswer6(null); x.setAnswer6Points(null); x.setQuestionSetVersionEntity(null); x.setCreated(null);}
            else if (x.getAnswer2Points().equals(max)) {x.setAnswer1(x.getAnswer2()); x.setAnswer1Points(max); x.setAnswer2(null); x.setAnswer2Points(null); x.setAnswer3(null); x.setAnswer3Points(null); x.setAnswer4(null); x.setAnswer4Points(null); x.setAnswer5(null); x.setAnswer5Points(null); x.setAnswer6(null); x.setAnswer6Points(null); x.setQuestionSetVersionEntity(null); x.setCreated(null);}
            else if (x.getAnswer3Points().equals(max)) {x.setAnswer1(x.getAnswer3()); x.setAnswer1Points(max); x.setAnswer2(null); x.setAnswer2Points(null); x.setAnswer3(null); x.setAnswer3Points(null); x.setAnswer4(null); x.setAnswer4Points(null); x.setAnswer5(null); x.setAnswer5Points(null); x.setAnswer6(null); x.setAnswer6Points(null); x.setQuestionSetVersionEntity(null); x.setCreated(null);}
            else if (x.getAnswer4Points().equals(max)) {x.setAnswer1(x.getAnswer4()); x.setAnswer1Points(max); x.setAnswer2(null); x.setAnswer2Points(null); x.setAnswer3(null); x.setAnswer3Points(null); x.setAnswer4(null); x.setAnswer4Points(null); x.setAnswer5(null); x.setAnswer5Points(null); x.setAnswer6(null); x.setAnswer6Points(null); x.setQuestionSetVersionEntity(null); x.setCreated(null);}
            else if (x.getAnswer5Points().equals(max)) {x.setAnswer1(x.getAnswer5()); x.setAnswer1Points(max); x.setAnswer2(null); x.setAnswer2Points(null); x.setAnswer3(null); x.setAnswer3Points(null); x.setAnswer4(null); x.setAnswer4Points(null); x.setAnswer5(null); x.setAnswer5Points(null); x.setAnswer6(null); x.setAnswer6Points(null); x.setQuestionSetVersionEntity(null); x.setCreated(null);}
            else                                       {x.setAnswer1(x.getAnswer6()); x.setAnswer1Points(max); x.setAnswer2(null); x.setAnswer2Points(null); x.setAnswer3(null); x.setAnswer3Points(null); x.setAnswer4(null); x.setAnswer4Points(null); x.setAnswer5(null); x.setAnswer5Points(null); x.setAnswer6(null); x.setAnswer6Points(null); x.setQuestionSetVersionEntity(null); x.setCreated(null);}
        }

        // wrap in a List and sort by Sequence#
        List<QuestionsEntity> correctAnswersList = new ArrayList<QuestionsEntity>(foundQuestionsEntities);
        correctAnswersList.sort(Comparator.comparing(QuestionsEntity::getSequenceNumber));

        return ResponseEntity.ok(correctAnswersList);
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

        QuestionsEntityDto savedQuestionsEntityDto = questionsEntityService.createQuestionsEntity(questionsEntityDto, questionSetVersionEntityId, user);
        return ResponseEntity.ok(savedQuestionsEntityDto);
    }

    // POST/DELETE  delete a question Manage/(AskFormQuestion)
    @RequestMapping(value = "/del", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteQuestionsEntity(
            @Valid
            @RequestBody final QuestionsEntityDto questionsEntityDto,
            @RequestHeader("Authorization") String token,
            final Long questionSetVersionEntityId) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        questionsEntityDto.setCreativeSource(user);  // for validation to allow only token user to delete own questions.
        questionsEntityService.deleteQuestionsEntity(questionsEntityDto);

        String allDeleted = "Deleted";
        return ResponseEntity.ok(allDeleted);
    }

            // for multiple files upload...
           // return Arrays.asList(files)
           // .stream()
           //     .map(file -> uploadFile(file))
           // .collect(Collectors.toList());

}