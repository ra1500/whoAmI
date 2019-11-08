package core.transformers;

import db.entity.UserAnswersEntity;
import model.UserAnswersEntityDto;
import org.springframework.stereotype.Component;

@Component
public class UserAnswersEntityDtoTransformer {

    public UserAnswersEntityDtoTransformer() {
    }

    // GET
    public UserAnswersEntityDto generate(final UserAnswersEntity userAnswersEntity) {
        if (userAnswersEntity == null || userAnswersEntity.getId() == null) {
            return null;
        }
        UserAnswersEntityDto dto = new UserAnswersEntityDto();
        dto.setId(userAnswersEntity.getId());
        dto.setCreated(userAnswersEntity.getCreated());
        dto.setUserName(userAnswersEntity.getUserName());
        dto.setAnswer(userAnswersEntity.getAnswer());
        dto.setAnswerPoints(userAnswersEntity.getAnswerPoints());
        dto.setAuditee(userAnswersEntity.getAuditee());
        dto.setComments(userAnswersEntity.getComments());
        return dto;
    }

    // POST
    public UserAnswersEntity generate(final UserAnswersEntityDto dto) {
        return new UserAnswersEntity(dto.getUserName(), dto.getAnswer(), dto.getAnswerPoints(),
                 dto.getAuditee(), dto.getComments());
    }
}
