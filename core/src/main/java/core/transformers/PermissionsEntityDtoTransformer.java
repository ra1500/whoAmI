package core.transformers;

import db.entity.PermissionsEntity;
import model.PermissionsEntityDto;
import org.springframework.stereotype.Component;

@Component
public class PermissionsEntityDtoTransformer {

    public PermissionsEntityDtoTransformer() {
    }

    // GET
    public PermissionsEntityDto generate(final PermissionsEntity permissionsEntity) {
        if (permissionsEntity == null || permissionsEntity.getGid() == null) {
            return null;
        }

        PermissionsEntityDto dto = new PermissionsEntityDto();
        //dto.setGid(permissionsEntity.getGid());
        //dto.setCreated(permissionsEntity.getCreated());
        dto.setUserName(permissionsEntity.getUserName());
        dto.setAuditee(permissionsEntity.getAuditee());
        dto.setNetworkProfilePagePermission(permissionsEntity.getNetworkProfilePagePermission());
        dto.setQuestionSetVersion(permissionsEntity.getQuestionSetVersion());
        dto.setTbd(permissionsEntity.getTbd());

        return dto;
    }

    public PermissionsEntity generate(final PermissionsEntityDto dto) {
        return new PermissionsEntity(dto.getUserName(),dto.getAuditee(), dto.getNetworkProfilePagePermission(),
                dto.getQuestionSetVersion(), dto.getTbd());
    }
}
