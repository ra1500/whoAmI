package main;

// import main.Paths;     --use later if wish to have Paths restricted/opened via separate class--
// import main.InquiryEntityService;
// import main.InquiryEntityDto;
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
@RequestMapping(value = "fire", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(description = "InquiryEntity endpoints", tags = "InquiryEntity")
public class InquiryEntityController extends AbstractRestController {

    private InquiryEntityService inquiryEntityService;

    public InquiryEntityController(InquiryEntityService inquiryEntityService) {
        this.inquiryEntityService = inquiryEntityService; }

    @ApiOperation(value = "getInquiryEntity")
    @RequestMapping(value = "/{gid}", method = RequestMethod.GET)
    public ResponseEntity<InquiryEntityDto> getInquiryEntity(
            @PathVariable("gid")
            final Long gid) {

        InquiryEntityDto inquiryEntityDto = inquiryEntityService.getInquiryEntity(gid);

        if (inquiryEntityDto == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(inquiryEntityDto);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InquiryEntityDto> createInquiryEntity(
            @Valid
            @RequestBody
            final InquiryEntityDto inquiryEntityDto) {
        InquiryEntityDto savedInquiryEntityDto = inquiryEntityService.createInquiryEntity(inquiryEntityDto);
        return ResponseEntity.ok(savedInquiryEntityDto);
    }
}
