package core.transformers;

import db.entity.QuestionSetVersionEntity;
import model.QuestionSetVersionEntityDto;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class QuestionSetVersionEntityDtoTransformer {

    public QuestionSetVersionEntityDtoTransformer() {
    }

    // GET
    public QuestionSetVersionEntityDto generate(final QuestionSetVersionEntity questionSetVersionEntity) {
        if (questionSetVersionEntity == null || questionSetVersionEntity.getId() == null) {
            return null;
        }
        QuestionSetVersionEntityDto dto = new QuestionSetVersionEntityDto();
        dto.setId(questionSetVersionEntity.getId());
        dto.setCreated(questionSetVersionEntity.getCreated());
        dto.setTitle(questionSetVersionEntity.getTitle());
        dto.setCategory(questionSetVersionEntity.getCategory());
        dto.setDescription(questionSetVersionEntity.getDescription());
        dto.setVersion(questionSetVersionEntity.getVersion());
        dto.setCreativeSource(questionSetVersionEntity.getCreativeSource());
        return dto;
    }

    // POST
    public QuestionSetVersionEntity generate(final QuestionSetVersionEntityDto dto) {
        return new QuestionSetVersionEntity( dto.getTitle(), dto.getCategory(), dto.getDescription(), dto.getVersion(),
                 dto.getCreativeSource());
    }
}
