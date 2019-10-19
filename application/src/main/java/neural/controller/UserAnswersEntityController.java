package neural.controller;

// import Paths;     --use later if wish to have Paths restricted/opened via separate class--
import core.services.UserAnswersEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.UserAnswersEntityDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@RestController
@RequestMapping(value = "a", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "UserAnswersEntity endpoints", tags = "UserAnswersEntity")
public class UserAnswersEntityController extends AbstractRestController {

    private UserAnswersEntityService userAnswersEntityService;

    public UserAnswersEntityController(UserAnswersEntityService userAnswersEntityService) {
        this.userAnswersEntityService = userAnswersEntityService;
    }

    @ApiOperation(value = "getUserAnswersEntity")
    @RequestMapping(value = "/{userName}/{questionId}", method = RequestMethod.GET)
    public ResponseEntity<UserAnswersEntityDto> getUserAnswersEntity(
            @PathVariable("userName")
            final String userName,

            @PathVariable("questionSetVersion")
            final Long questionSetVersion,

            @PathVariable("questionId")
            final Long questionId) {

        UserAnswersEntityDto userAnswersEntityDto = userAnswersEntityService.getUserAnswersEntity(userName,questionId);

        if (userAnswersEntityDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(userAnswersEntityDto);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserAnswersEntityDto> createUserAnswersEntity(
            @Valid
            @RequestBody
            final UserAnswersEntityDto userAnswersEntityDto) {
        UserAnswersEntityDto savedUserAnswersEntityDto = userAnswersEntityService.createUserAnswersEntity(userAnswersEntityDto);
        return ResponseEntity.ok(savedUserAnswersEntityDto);
    }
}
