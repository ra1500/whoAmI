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
        if (userAnswersEntity == null || userAnswersEntity.getGid() == null) {
            return null;
        }

        UserAnswersEntityDto dto = new UserAnswersEntityDto();
        dto.setGid(userAnswersEntity.getGid());
        dto.setCreated(userAnswersEntity.getCreated());
        dto.setUserName(userAnswersEntity.getUserName());
        dto.setQuestionId(userAnswersEntity.getQuestionId());
        dto.setAnswer(userAnswersEntity.getAnswer());
        dto.setAnswerPoints(userAnswersEntity.getAnswerPoints());
        dto.setQuestionSetVersion(userAnswersEntity.getQuestionSetVersion());
        dto.setAuditee(userAnswersEntity.getAuditee());
        dto.setComments(userAnswersEntity.getComments());

        return dto;
    }

    public UserAnswersEntity generate(final UserAnswersEntityDto dto) {
        return new UserAnswersEntity(dto.getUserName(),dto.getQuestionId(), dto.getAnswer(), dto.getAnswerPoints(),
                dto.getQuestionSetVersion(), dto.getAuditee(), dto.getComments());
    }
}
