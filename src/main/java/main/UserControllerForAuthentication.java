package main;

// import main.Paths;     --use later if wish to have Paths restricted/opened via separate class--
// import main.UserEntityService;
// import main.UserEntityDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Component  // is this needed?
@RestController
@RequestMapping(value = "/do_login",
        produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "UserEntity for authentication",
        tags = "UserEntity")  // ???
public class UserControllerForAuthentication extends AbstractRestController {

    private UserEntityService userEntityService;

    public UserControllerForAuthentication(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserEntityDto> getAuthenticationToken(
            @Valid
            @RequestBody
            final UserEntityDto requestedUserEntityDto) {

            UserEntityDto foundUserEntityDto = userEntityService.getUserEntity(requestedUserEntityDto);
            CustomAuthenticationProvider customauthenticationProvider = new CustomAuthenticationProvider();
            customauthenticationProvider.setFoundUserAndPassword(foundUserEntityDto);

            //Authentication auth;// = new Authentication();  // is this needed? how is it injected in CustomAuthenticationProvder.java if it isn't initialized?
            //customauthenticationProvider.authenticate(Authentication);
            return ResponseEntity.ok(requestedUserEntityDto);
    }
}

