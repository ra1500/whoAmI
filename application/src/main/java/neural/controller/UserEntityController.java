package neural.controller;

// import Paths;     --use later if wish to have Paths restricted/opened via separate class--
import core.services.UserEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.UserEntityDto;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "UserEntity endpoints", tags = "UserEntity")
public class UserEntityController extends AbstractRestController {

    private UserEntityService userEntityService;

    public UserEntityController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    // GET a user (and user's friendships)
    @ApiOperation(value = "getUserEntity")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<UserEntityDto> getUserEntity(
            @RequestHeader("Authorization") String token)               {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        UserEntityDto userEntityDto = userEntityService.getUserEntity(user);
        userEntityDto.setPassword(null);
        if (userEntityDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
         }
        return ResponseEntity.ok(userEntityDto);
    }

    // GET a user (without the friendships load). so user knows publicProfile privacy setting.
    @ApiOperation(value = "getUserEntity")
    @RequestMapping(value = "/pr", method = RequestMethod.GET)
    public ResponseEntity<UserEntityDto> getUserEntity2(
            @RequestHeader("Authorization") String token)               {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        UserEntityDto userEntityDto = userEntityService.getUserEntity(user);
        userEntityDto.setPassword(null);
        userEntityDto.setFriendsList(null);
        userEntityDto.setUserName(null);
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
        return ResponseEntity.ok(savedUserEntityDto);
    }

    // POST from client Login form. Check if user exists. Return token.
    @RequestMapping(value = "/userId",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserEntityDto> verifyLoginUserEntity(
            @Valid
            @RequestBody
            final UserEntityDto userEntityDto) {
        UserEntityDto verifiedUserEntityDto = userEntityService.getUserEntity(userEntityDto.getUserName(),userEntityDto.getPassword());
        if (verifiedUserEntityDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(verifiedUserEntityDto);
    }

    // PATCH a user's publicProfile privacy setting
    @RequestMapping(value = "/up", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserEntityDto> patchPublicProfile(
            @RequestHeader("Authorization") String token,
            @Valid
            @RequestBody
            final UserEntityDto userEntityDto) {

        // getting userName from Authorization token to secure endpoint.
        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];
        String password = values[1];

        // setting/securing DTO userName as obtained from the Authorization token.
        userEntityDto.setUserName(user);
        userEntityDto.setPassword(password); // password cannot be null for a post/patch

        UserEntityDto patchedUserEntityDto = userEntityService.patchUserEntity(userEntityDto);
        patchedUserEntityDto.setPassword(null); //outgoing dto shouldnt have the password.

        return ResponseEntity.ok(patchedUserEntityDto);
    }
}
