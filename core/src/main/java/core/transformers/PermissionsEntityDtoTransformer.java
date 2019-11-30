package core.transformers;

import db.entity.PermissionsEntity;
import db.entity.QuestionSetVersionEntity;
import db.repository.PermissionsRepositoryDAO;
import db.repository.QuestionSetVersionRepositoryDAO;
import model.PermissionsEntityDto;
import org.springframework.stereotype.Component;

@Component
public class PermissionsEntityDtoTransformer {

    public PermissionsEntityDtoTransformer() {
    }

    // GET
    public PermissionsEntityDto generate(final PermissionsEntity permissionsEntity) {
        if (permissionsEntity == null || permissionsEntity.getId() == null) {
            return null;
        }
        PermissionsEntityDto dto = new PermissionsEntityDto();
        dto.setId(permissionsEntity.getId());
        dto.setCreated(permissionsEntity.getCreated());
        dto.setUserName(permissionsEntity.getUserName());
        dto.setAuditee(permissionsEntity.getAuditee());
        dto.setViewGroup(permissionsEntity.getViewGroup());
        dto.setType(permissionsEntity.getType());
        dto.setTypeNumber(permissionsEntity.getTypeNumber());
        dto.setScore(permissionsEntity.getScore());
        dto.setResult(permissionsEntity.getResult());
        dto.setBadge(permissionsEntity.getBadge());
        dto.setQuestionSetVersionEntity(permissionsEntity.getQuestionSetVersionEntity());
        return dto;
    }

    // POST
    public PermissionsEntity generate(final PermissionsEntityDto dto) {
        return new PermissionsEntity( dto.getUserName(), dto.getAuditee(), dto.getViewGroup(),
                 dto.getType(), dto.getTypeNumber(), dto.getScore(), dto.getResult(), dto.getBadge(),dto.getQuestionSetVersionEntity());
    }
}
