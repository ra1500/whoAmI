package neural.controller;

// import Paths;     --use later if wish to have Paths restricted/opened via separate class--
import core.services.UserAnswersEntityService;
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
    @RequestMapping(value = "/{qId}/{qsId}/{au}", method = RequestMethod.GET)
    public ResponseEntity<UserAnswersEntityDto> getUserAnswersEntity(
            @RequestHeader("Authorization") String token,
            @PathVariable("au") final String auditee,
            @PathVariable("qsId") final Long questionSetVersion,
            @PathVariable("qId") final Long questionId) {
        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        UserAnswersEntityDto userAnswersEntityDto = userAnswersEntityService.getUserAnswersEntity(user, auditee, questionSetVersion, questionId );
        if (userAnswersEntityDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(userAnswersEntityDto);
    }

    // POST a user's answer to a question. secured with token's userName.
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAnswersEntityDto> createUserAnswersEntity(
            @Valid
            @RequestBody
            final UserAnswersEntityDto userAnswersEntityDto,
            @RequestHeader("Authorization") String token) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        // userName from token
        userAnswersEntityDto.setUserName(user);

        UserAnswersEntityDto savedUserAnswersEntityDto = userAnswersEntityService.createUserAnswersEntity(userAnswersEntityDto);
        return ResponseEntity.ok(savedUserAnswersEntityDto);
    }

    // POST delete all of a user's answers
    @RequestMapping(value = "/del",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteAllUserAnswers(
            @Valid
            @RequestBody
            final UserAnswersEntityDto userAnswersEntityDto,
            @RequestHeader("Authorization") String token) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        userAnswersRepositoryDAO.deleteAllByUserNameAndAuditeeAndQuestionSetVersion(user, userAnswersEntityDto.getAuditee(), userAnswersEntityDto.getQuestionSetVersion());
        String allDeleted = "{ all gone }";
        return ResponseEntity.ok(allDeleted);
    }
}
