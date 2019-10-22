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

@RestController
@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "UserEntity endpoints", tags = "UserEntity")
public class UserEntityController extends AbstractRestController {

    private UserEntityService userEntityService;

    public UserEntityController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @ApiOperation(value = "getUserEntity")
    @RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    public ResponseEntity<UserEntityDto> getUserEntity(
            @PathVariable("userName")
            final String userName) {
    //TODO: check that authorization token username same as requested userEntity otherwise block
        UserEntityDto userEntityDto = userEntityService.getUserEntity(userName);
        userEntityDto.setPassword(null);


        if (userEntityDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         }

        return ResponseEntity.ok(userEntityDto);
    }

    // POST to add a new user
    @RequestMapping(value = "/signup",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserEntityDto> createUserEntity(
            @Valid
            @RequestBody
            final UserEntityDto userEntityDto) {
        UserEntityDto savedUserEntityDto = userEntityService.createUserEntity(userEntityDto);
        savedUserEntityDto.setPassword(null);
        return ResponseEntity.ok(savedUserEntityDto);
    }

    // POST from client Login form. Check if user exists. Return userId.
    @RequestMapping(value = "/userId",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserEntityDto> verifyLoginUserEntity(
            @Valid
            @RequestBody
            final UserEntityDto userEntityDto) {
        UserEntityDto verifiedUserEntityDto = userEntityService.getUserEntity(userEntityDto.getUserName(),userEntityDto.getPassword());
        if (verifiedUserEntityDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        //verifiedUserEntityDto.setPassword(null); TODO: stop sending password. userId and password should be encrypted
        return ResponseEntity.ok(verifiedUserEntityDto);
    }
}
