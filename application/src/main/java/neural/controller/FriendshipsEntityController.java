package neural.controller;

// import .Paths;     --use later if wish to have Paths restricted/opened via separate class--
import db.entity.UserEntity;
import db.repository.UserRepositoryDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.FriendshipsEntityDto;
import core.services.FriendshipsEntityService;
import model.FriendshipsEntityDtoPOST;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
@RequestMapping(value = "f", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "FriendshipsEntity endpoints", tags = "FriendshipsEntity")
public class FriendshipsEntityController extends AbstractRestController {

    private FriendshipsEntityService friendshipsEntityService;

    // to use to see if friend exists before POSTing to db after invitation
    private UserRepositoryDAO userRepositoryDAO;

    public FriendshipsEntityController(FriendshipsEntityService friendshipsEntityService, UserRepositoryDAO userRepositoryDAO) {
        this.friendshipsEntityService = friendshipsEntityService;
        this.userRepositoryDAO = userRepositoryDAO;}

    // POST a friendship
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FriendshipsEntityDto> createFriendshipsEntity(
            @RequestHeader("Authorization") String token,
            @Valid
            @RequestBody
            final FriendshipsEntityDtoPOST friendshipsEntityDtoPOST) {

        // does friend exist service.
        UserEntity userEntity = userRepositoryDAO.findOneByUserName(friendshipsEntityDtoPOST.getFriend());

        if (userEntity != null) {

            // getting userName from Authorization token to secure endpoint.
            String base64Credentials = token.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            final String[] values = credentials.split(":", 2);
            String user = values[0];

            // setting/securing DTO userName as obtained from the Authorization token.
            friendshipsEntityDtoPOST.setUserName(user);

            // Main/first db entry before doubling below
            FriendshipsEntityDto savedFriendshipsEntityDtoPOST = friendshipsEntityService.createFriendshipsEntity(friendshipsEntityDtoPOST);

            // Doubled entry but friend becomes user and user becomes friend. This ensures a User has full control over their relationships.
            // Thus 'One User to Many Friends' instead of ManyToMany.
            String friendToUser = friendshipsEntityDtoPOST.getFriend();
            String userToFriend = friendshipsEntityDtoPOST.getUserName();
            FriendshipsEntityDtoPOST friendshipsEntityDtoPOSTdouble = friendshipsEntityDtoPOST;
            friendshipsEntityDtoPOSTdouble.setFriend(userToFriend);
            friendshipsEntityDtoPOSTdouble.setUserName(friendToUser);
            FriendshipsEntityDto savedFriendshipsEntityDtoPOSTdouble = friendshipsEntityService.createFriendshipsEntity(friendshipsEntityDtoPOSTdouble);

            return ResponseEntity.ok(savedFriendshipsEntityDtoPOST);
        } // end if

        else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } // end else
    }
}
