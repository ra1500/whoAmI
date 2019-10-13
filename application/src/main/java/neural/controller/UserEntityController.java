package neural.controller;

// import Paths;     --use later if wish to have Paths restricted/opened via separate class--
import core.services.UserEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.UserEntityDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@RestController
@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "UserEntity endpoints", tags = "UserEntity")
public class UserEntityController extends AbstractRestController {

    private UserEntityService userEntityService;

    public UserEntityController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @ApiOperation(value = "getUserEntity")
    @RequestMapping(value = "/{userName}/{password}", method = RequestMethod.GET)
    public ResponseEntity<UserEntityDto> getUserEntity(
            @PathVariable("userName")
            final String userName,

            @PathVariable("password")
            final String password) {

        UserEntityDto userEntityDto = userEntityService.getUserEntity(userName,password);

        if (userEntityDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(userEntityDto);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserEntityDto> createUserEntity(
            @Valid
            @RequestBody
            final UserEntityDto userEntityDto) {
        UserEntityDto savedUserEntityDto = userEntityService.createUserEntity(userEntityDto);
        return ResponseEntity.ok(savedUserEntityDto);
    }
}
