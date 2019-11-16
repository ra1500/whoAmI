package neural.controller;

import core.services.PermissionsEntityService;
import db.entity.PermissionsEntity;
import db.repository.FriendshipsRepositoryDAO;
import db.repository.PermissionsRepositoryDAO;
import db.repository.UserRepositoryDAO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.PermissionsEntityDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;

@RestController
@RequestMapping(value = "prm", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Permissions endpoints", tags = "PermissionsEntity")
public class PermissionsController extends AbstractRestController {

    private PermissionsEntityService permissionsEntityService;
    private PermissionsRepositoryDAO permissionsRepositoryDAO; // used for delete. shortcut to the repository.
    private FriendshipsRepositoryDAO friendshipsRepositoryDAO; // shortcut. TODO delete this and go through transformer

    public PermissionsController(PermissionsEntityService permissionsEntityService, PermissionsRepositoryDAO permissionsRepositoryDAO, FriendshipsRepositoryDAO friendshipsRepositoryDAO) {
        this.permissionsEntityService = permissionsEntityService;
        this.permissionsRepositoryDAO = permissionsRepositoryDAO;
        this.friendshipsRepositoryDAO = friendshipsRepositoryDAO;
    }

    // GET
    @ApiOperation(value = "permissionsEntity")
    @RequestMapping(value = "/{au}/{qsId}", method = RequestMethod.GET)
    public ResponseEntity<PermissionsEntityDto> getPermissionsEntity(
            @RequestHeader("Authorization") String token,
            @PathVariable("au") final String auditee,
            @PathVariable("qId") final Long questionSetVersionEntityId) {
        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        PermissionsEntityDto permissionsEntityDto = permissionsEntityService.getPermissionsEntity(user, auditee, questionSetVersionEntityId);
        if (permissionsEntityDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(permissionsEntityDto);
    }

    // POST/PATCH  posts a new one, updates an existing one
    @RequestMapping(value = "/sc/d{qsId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PermissionsEntityDto> createPermissionsEntity(
            @Valid
            @RequestBody final PermissionsEntityDto permissionsEntityDto,
            @RequestHeader("Authorization") String token,
            @RequestParam("qsId") final Long questionSetVersionEntityId) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        // userName from token
        permissionsEntityDto.setUserName(user);

        PermissionsEntityDto savedPermissionsEntityDto = permissionsEntityService.createPermissionsEntity(permissionsEntityDto, questionSetVersionEntityId, user);
        return ResponseEntity.ok(savedPermissionsEntityDto);
    }

    // POST/PATCH  post to let a group of connections view new user Qset
    @RequestMapping(value = "/sc/n{qsId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createFriendsViewQsetsPermissionsEntities(
            @Valid
            @RequestBody final PermissionsEntityDto permissionsEntityDto,
            @RequestHeader("Authorization") String token,
            @RequestParam("qsId") final Long questionSetVersionEntityId) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        String savedQsetViewPermissionsEntities = permissionsEntityService.createQsetViewPermissionEntities(permissionsEntityDto.getTypeNumber(), questionSetVersionEntityId, user);
        return ResponseEntity.ok(savedQsetViewPermissionsEntities);
    }

    // POST/PATCH  post to let an individual connection view new user Qset
    @RequestMapping(value = "/sc/o{qsId}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createIndividualViewQsetsPermissionsEntities(
            @Valid
            @RequestBody final PermissionsEntityDto permissionsEntityDto,
            @RequestHeader("Authorization") String token,
            @RequestParam("qsId") final Long questionSetVersionEntityId) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        String savedQsetViewPermissionsEntities = permissionsEntityService.createIndividualQsetViewPermissionEntity(questionSetVersionEntityId, user, permissionsEntityDto.getUserName());
        return ResponseEntity.ok(savedQsetViewPermissionsEntities);
    }

    // GET. QSets & user scores for Private Profile Page.
    @ApiOperation(value = "permissionsEntity")
    @RequestMapping(value = "/sc/dr", method = RequestMethod.GET)
    public ResponseEntity<Set<PermissionsEntity>> getPermissionsEntityUserScorePrivateProfilePage(
            @RequestHeader("Authorization") String token) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        //PermissionsEntityDto permissionsEntityDto = permissionsEntityService.getPermissionsEntity(user);

        // TODO: Create a set of Dto's in the transformer and return them as a Set instead of direct to repository.
        Set<PermissionsEntity> permissionsEntities = permissionsRepositoryDAO.getPrivateProfilePageQsets(user);

        if (permissionsEntities == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(permissionsEntities);
    }

    // GET. QSets for Main/'Scores' page.
    @ApiOperation(value = "permissionsEntity")
    @RequestMapping(value = "/sc/dw", method = RequestMethod.GET)
    public ResponseEntity<Set<PermissionsEntity>> getPermissionsEntityScoresPage(
            @RequestHeader("Authorization") String token) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        //PermissionsEntityDto permissionsEntityDto = permissionsEntityService.getPermissionsEntity(user);

        // TODO: Create a set of Dto's in the transformer and return them as a Set instead of direct to repository.
        Set<PermissionsEntity> permissionsEntities = permissionsRepositoryDAO.getScoresPageQsets();

        if (permissionsEntities == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(permissionsEntities);
    }

    // GET. QSets & user scores for Public Profile Page.
    @ApiOperation(value = "permissionsEntity")
    @RequestMapping(value = "/sc/dc", method = RequestMethod.GET)
    public ResponseEntity<Set<PermissionsEntity>> getPermissionsEntityUserScorePublicProfilePage(
            @RequestHeader("Authorization") String token) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        // TODO: Create a set of Dto's in the transformer and return them as a Set instead of direct to repository.
        Set<PermissionsEntity> permissionsEntities = permissionsRepositoryDAO.getPublicProfilePageQsets(user);

        if (permissionsEntities == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(permissionsEntities);
    }

    // GET. QSets & user scores for a Network Contact
    @ApiOperation(value = "permissionsEntity")
    @RequestMapping(value = "/sc/df{ctc}", method = RequestMethod.GET)
    public ResponseEntity<Set<PermissionsEntity>> getPermissionsEntityUserScoreNetworkContact(
            @RequestHeader("Authorization") String token,
            @RequestParam("ctc") final Long friendId) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        String friend = friendshipsRepositoryDAO.findOneById(friendId).getFriend(); // TODO clean this up to transformer

        // TODO: Create a set of Dto's in the transformer and return them as a Set instead of direct to repository.
        Set<PermissionsEntity> permissionsEntities = permissionsRepositoryDAO.getNetworkContactQsets(user, friend);

        if (permissionsEntities == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(permissionsEntities);
    }

    // GET. Private Profile page self-made Qsets.
    @ApiOperation(value = "permissionsEntity")
    @RequestMapping(value = "/sc/du", method = RequestMethod.GET)
    public ResponseEntity<Set<PermissionsEntity>> getPermissionsEntityPrivateProfilePage(
            @RequestHeader("Authorization") String token) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        // TODO: Create a set of Dto's in the transformer and return them as a Set instead of direct to repository.
        Set<PermissionsEntity> permissionsEntities = permissionsRepositoryDAO.getPrivateProfilePageSelfMadeQsets(user);

        if (permissionsEntities == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(permissionsEntities);
    }

    // POST/DELETE. Delete a score completely from permissionsEntity
    @ApiOperation(value = "permissionsEntity")
    @RequestMapping(value = "/sc/dl", method = RequestMethod.POST)
    public ResponseEntity<Integer> deletePermissionsEntityUserScorePrivateProfilePage(
            @Valid
            @RequestBody final PermissionsEntityDto permissionsEntityDto,
            @RequestHeader("Authorization") String token) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        Integer deletedScore = permissionsRepositoryDAO.deleteOneById(permissionsEntityDto.getId());

        if (deletedScore == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(deletedScore);
    }
}