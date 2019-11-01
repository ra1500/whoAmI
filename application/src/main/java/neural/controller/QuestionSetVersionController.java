package neural.controller;

import core.services.QuestionSetVersionEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.QuestionSetVersionEntityDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping(value = "qs", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "QuestionsEntity endpoints", tags = "QuestionsEntity")
public class QuestionSetVersionController extends AbstractRestController {

    private QuestionSetVersionEntityService questionSetVersionEntityService;

    public QuestionSetVersionController(QuestionSetVersionEntityService questionSetVersionEntityService) {
        this.questionSetVersionEntityService = questionSetVersionEntityService; }

    // GET questionSetVersion. DTO excludes List<Questions> to reduce load.
    @ApiOperation(value = "getQuestionsEntity")
    @RequestMapping(value = "/{qsid}", method = RequestMethod.GET)
    public ResponseEntity<QuestionSetVersionEntityDto> getQuestionSetVersionEntity(
            @RequestHeader("Authorization") String token,

            @PathVariable("qsid") //questionSetNumber
            final Integer qsid) {

        // secured by token
        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        QuestionSetVersionEntityDto questionSetVersionEntityDto = questionSetVersionEntityService.getQuestionSetVersionEntity(qsid);

        if (questionSetVersionEntityDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(questionSetVersionEntityDto);
    }


}
