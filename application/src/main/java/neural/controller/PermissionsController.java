package neural.controller;

import core.services.PermissionsEntityService;
import db.repository.PermissionsRepositoryDAO;
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

@RestController
@RequestMapping(value = "prm", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "Permissions endpoints", tags = "PermissionsEntity")
public class PermissionsController extends AbstractRestController {

    private PermissionsEntityService permissionsEntityService;
    private PermissionsRepositoryDAO permissionsRepositoryDAO; // used for delete. shortcut to the repository.

    public PermissionsController(PermissionsEntityService permissionsEntityService, PermissionsRepositoryDAO permissionsRepositoryDAO) {
        this.permissionsEntityService = permissionsEntityService;
        this.permissionsRepositoryDAO = permissionsRepositoryDAO;
    }

    // GET
    @ApiOperation(value = "permissionsEntity")
    @RequestMapping(value = "/{au}/{qsId}", method = RequestMethod.GET)
    public ResponseEntity<PermissionsEntityDto> getPermissionsEntity(
            @RequestHeader("Authorization") String token,
            @PathVariable("au") final String auditee,
            @PathVariable("qId") final Long questionSetVersion) {
        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        PermissionsEntityDto permissionsEntityDto = permissionsEntityService.getPermissionsEntity(user, auditee, questionSetVersion);
        if (permissionsEntityDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(permissionsEntityDto);
    }

    // POST
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PermissionsEntityDto> createPermissionsEntity(
            @Valid
            @RequestBody final PermissionsEntityDto permissionsEntityDto,
            @RequestHeader("Authorization") String token) {

        String base64Credentials = token.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        // credentials = username:password
        final String[] values = credentials.split(":", 2);
        String user = values[0];

        // userName from token
        permissionsEntityDto.setUserName(user);

        PermissionsEntityDto savedPermissionsEntityDto = permissionsEntityService.createPermissionsEntity(permissionsEntityDto);
        return ResponseEntity.ok(savedPermissionsEntityDto);
    }
}