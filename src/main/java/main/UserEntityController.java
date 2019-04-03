package main;

// import main.Paths;     --use later if wish to have Paths restricted/opened via separate class--
// import main.UserEntityService;
// import main.UserEntityDto;
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
@RequestMapping(value = "user",
        produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "UserEntity endpoints",
        tags = "UserEntity")
public class UserEntityController extends AbstractRestController {

    private UserEntityService userEntityService;

    public UserEntityController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @ApiOperation(value = "getUserEntity")
    @RequestMapping(value = "/{userName}/{password}",
            method = RequestMethod.GET)
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

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserEntityDto> createUserEntity(
            @Valid
            @RequestBody
            final UserEntityDto userEntityDto) {
        UserEntityDto savedUserEntityDto = userEntityService.createUserEntity(userEntityDto);
        return ResponseEntity.ok(savedUserEntityDto);
    }
}
