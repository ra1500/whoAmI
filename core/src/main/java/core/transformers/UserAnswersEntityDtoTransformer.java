package core.transformers;

import db.entity.UserAnswersEntity;
import model.UserAnswersEntityDto;
import org.springframework.stereotype.Component;

@Component
public class UserAnswersEntityDtoTransformer {

    public UserAnswersEntityDtoTransformer() {
    }

    public UserAnswersEntityDto generate(final UserAnswersEntity userAnswersEntity) {
        if (userAnswersEntity == null || userAnswersEntity.getGid() == null) {
            return null;
        }

        UserAnswersEntityDto dto = new UserAnswersEntityDto();
        dto.setGid(userAnswersEntity.getGid());
        dto.setCreated(userAnswersEntity.getCreated());
        dto.setUserId(userAnswersEntity.getUserId());
        dto.setQuestionId(userAnswersEntity.getQuestionId());
        dto.setAnswer(userAnswersEntity.getAnswer());
        dto.setAnswerPoints(userAnswersEntity.getAnswerPoints());
        dto.setQuestionSetVersion(userAnswersEntity.getQuestionSetVersion());

        return dto;
    }

    public UserAnswersEntity generate(final UserAnswersEntityDto dto) {
        return new UserAnswersEntity(dto.getUserId(),dto.getQuestionId(), dto.getQuestionSetVersion(),dto.getAnswerPoints(),dto.getAnswer());
    }
}
