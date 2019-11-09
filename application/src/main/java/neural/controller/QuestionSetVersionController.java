package neural.controller;

import core.services.QuestionSetVersionEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.QuestionSetVersionEntityDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping(value = "qs", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "QuestionsEntity endpoints", tags = "QuestionsEntity")
public class QuestionSetVersionController extends AbstractRestController {

    private QuestionSetVersionEntityService questionSetVersionEntityService;

    public QuestionSetVersionController(QuestionSetVersionEntityService questionSetVersionEntityService) {
        this.questionSetVersionEntityService = questionSetVersionEntityService; }

    // GET questionSetVersion. DTO excludes Set<Questions> to reduce load.
    @ApiOperation(value = "getQuestionsEntity")
    @RequestMapping(value = "/g{qsid}", method = RequestMethod.GET)
    public ResponseEntity<QuestionSetVersionEntityDto> getQuestionSetVersionEntity(
            @RequestHeader("Authorization") String token,
            @RequestParam("qsid") final Long qsid) {

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

    // POST/PATCH  posts a new one, updates an existing one
    @RequestMapping(value = "/p{qsid}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<QuestionSetVersionEntityDto> createQuestionSetVersionEntity(
            @Valid
            @RequestBody final QuestionSetVersionEntityDto questionSetVersionEntityDto,
            @RequestHeader("Authorization") String token,
            @RequestParam("qsid") final Long qsid) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        // userName from token
        questionSetVersionEntityDto.setCreativeSource(user);

        QuestionSetVersionEntityDto savedQuestionSetVersionEntityDto = questionSetVersionEntityService.createQuestionSetVersionEntity(questionSetVersionEntityDto, qsid, user);
        return ResponseEntity.ok(savedQuestionSetVersionEntityDto);
    }

}
