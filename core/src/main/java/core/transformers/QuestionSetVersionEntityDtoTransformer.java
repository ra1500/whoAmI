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
        dto.setScoringStyle(questionSetVersionEntity.getScoringStyle());
        dto.setBadges(questionSetVersionEntity.getBadges());
        dto.setResult1(questionSetVersionEntity.getResult1());
        dto.setResult2(questionSetVersionEntity.getResult2());
        dto.setResult3(questionSetVersionEntity.getResult3());
        dto.setResult4(questionSetVersionEntity.getResult4());
        dto.setResult1start(questionSetVersionEntity.getResult1start());
        dto.setResult2start(questionSetVersionEntity.getResult2start());
        dto.setResult3start(questionSetVersionEntity.getResult3start());
        dto.setResult4start(questionSetVersionEntity.getResult4start());
        return dto;
    }

    // POST
    public QuestionSetVersionEntity generate(final QuestionSetVersionEntityDto dto) {
        return new QuestionSetVersionEntity( dto.getTitle(), dto.getCategory(), dto.getDescription(), dto.getVersion(),
                 dto.getCreativeSource(), dto.getScoringStyle(), dto.getBadges(), dto.getResult1(), dto.getResult2(), dto.getResult3(), dto.getResult4(),
        dto.getResult1start(), dto.getResult2start(), dto.getResult3start(), dto.getResult4start());
    }
}
