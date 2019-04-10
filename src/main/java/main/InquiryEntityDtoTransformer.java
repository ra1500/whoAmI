package main;

//import main.InquiryEntity;
//import main.InquiryEntityDto;
import org.springframework.stereotype.Component;

@Component
public class InquiryEntityDtoTransformer {

    public InquiryEntityDtoTransformer() {
    }

    public InquiryEntityDto generate(final InquiryEntity inquiryEntity) {
        if (inquiryEntity == null || inquiryEntity.getGid() == null) {
            return null;
        }

        InquiryEntityDto dto = new InquiryEntityDto();
        dto.setGid(inquiryEntity.getGid());
        dto.setCreated(inquiryEntity.getCreated());
        dto.setCategoryId(inquiryEntity.getCategoryId());
        dto.setDescription(inquiryEntity.getDescription());
        dto.setRawCsvData(inquiryEntity.getRawCsvData());
        dto.setUsername(inquiryEntity.getUsername());

        return dto;
    }

    public InquiryEntity generate(final InquiryEntityDto dto) {
        return new InquiryEntity(dto.getCategoryId(),dto.getDescription(),dto.getRawCsvData(), dto.getUsername());
    }
}
