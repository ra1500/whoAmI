package neural.controller;

// import .Paths;     --use later if wish to have Paths restricted/opened via separate class--
import db.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.FriendshipsEntityDto;
import core.services.FriendshipsEntityService;
import model.FriendshipsEntityDtoPOST;
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
@RequestMapping(value = "f", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "FriendshipsEntity endpoints", tags = "FriendshipsEntity")
public class FriendshipsEntityController extends AbstractRestController {

    private FriendshipsEntityService friendshipsEntityService;
    private UserEntity userEntity;  // ? not needed?

    public FriendshipsEntityController(FriendshipsEntityService friendshipsEntityService) {
        this.friendshipsEntityService = friendshipsEntityService; }

    // GET a single friendship. not used.....
    //@ApiOperation(value = "getFriendshipEntity")
    //@RequestMapping(value = "/{userName}", method = RequestMethod.GET)
    //public ResponseEntity<FriendshipsEntityDto> getFriendshipsEntity(
    //        @PathVariable("userName")
    //        final String userName) {

     //   userEntity.setUserName(userName); // not needed?
     //   FriendshipsEntityDto friendshipsEntityDto = friendshipsEntityService.getFriendshipsEntity(userEntity);

     //   if (friendshipsEntityDto == null) {
     //       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     //   }

      //  return ResponseEntity.ok(friendshipsEntityDto);
    //}

    // POST a friendship
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FriendshipsEntityDto> createFriendshipsEntity(
            @Valid
            @RequestBody
            final FriendshipsEntityDtoPOST friendshipsEntityDtoPOST) {
        // Main entry before doubling below
        FriendshipsEntityDto savedFriendshipsEntityDtoPOST = friendshipsEntityService.createFriendshipsEntity(friendshipsEntityDtoPOST);

        // Doubled entry but friend becomes user and user becomes friend. This ensures a User has full control over their relationships. Thus the 'One User to Many Friends' instead of ManyToMany.
        String friendToUser = friendshipsEntityDtoPOST.getFriend();
        String userToFriend = friendshipsEntityDtoPOST.getUserName();
        FriendshipsEntityDtoPOST  friendshipsEntityDtoPOSTdouble = friendshipsEntityDtoPOST;
        friendshipsEntityDtoPOSTdouble.setFriend(userToFriend);
        friendshipsEntityDtoPOSTdouble.setUserName(friendToUser);
        FriendshipsEntityDto savedFriendshipsEntityDtoPOSTdouble = friendshipsEntityService.createFriendshipsEntity(friendshipsEntityDtoPOSTdouble);

        return ResponseEntity.ok(savedFriendshipsEntityDtoPOST);

    }
}
