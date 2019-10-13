package core.transformers;

import db.entity.QuestionSetVersionEntity;
import model.QuestionSetVersionEntityDto;
import org.springframework.stereotype.Component;

@Component
public class QuestionSetVersionEntityDtoTransformer {

    public QuestionSetVersionEntityDtoTransformer() {
    }

    public QuestionSetVersionEntityDto generate(final QuestionSetVersionEntity questionSetVersionEntity) {
        if (questionSetVersionEntity == null || questionSetVersionEntity.getGid() == null) {
            return null;
        }

        QuestionSetVersionEntityDto dto = new QuestionSetVersionEntityDto();
        dto.setGid(questionSetVersionEntity.getGid());
        dto.setCreated(questionSetVersionEntity.getCreated());
        dto.setQuestionIdList(questionSetVersionEntity.getQuestionIdList());

        return dto;
    }

    public QuestionSetVersionEntity generate(final QuestionSetVersionEntityDto dto) {
        return new QuestionSetVersionEntity(dto.getQuestionIdList());
    }
}
