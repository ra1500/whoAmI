package core.transformers;

import db.entity.QuestionsEntity;
import model.QuestionsEntityDto;
import org.springframework.stereotype.Component;

@Component
public class QuestionsEntityDtoTransformer {

    public QuestionsEntityDtoTransformer() {
    }

    // GET from db
    public QuestionsEntityDto generate(final QuestionsEntity questionsEntity) {
        if (questionsEntity == null || questionsEntity.getId() == null) {
            return null;
        }
        QuestionsEntityDto dto = new QuestionsEntityDto();
        dto.setId(questionsEntity.getId());
        dto.setCreated(questionsEntity.getCreated());
        dto.setSequenceNumber(questionsEntity.getSequenceNumber());
        dto.setCreativeSource(questionsEntity.getCreativeSource());
        dto.setQuestion(questionsEntity.getQuestion());
        dto.setCategory(questionsEntity.getCategory());
        dto.setMaxPoints(questionsEntity.getMaxPoints());
        dto.setAnswer1(questionsEntity.getAnswer1());
        dto.setAnswer1Points(questionsEntity.getAnswer1Points());
        dto.setAnswer2(questionsEntity.getAnswer2());
        dto.setAnswer2Points(questionsEntity.getAnswer2Points());
        dto.setAnswer3(questionsEntity.getAnswer3());
        dto.setAnswer3Points(questionsEntity.getAnswer3Points());
        dto.setAnswer4(questionsEntity.getAnswer4());
        dto.setAnswer4Points(questionsEntity.getAnswer4Points());
        dto.setAnswer5(questionsEntity.getAnswer5());
        dto.setAnswer5Points(questionsEntity.getAnswer5Points());
        dto.setAnswer6(questionsEntity.getAnswer6());
        dto.setAnswer6Points(questionsEntity.getAnswer6Points());
        return dto;
    }

    // POST
    public QuestionsEntity generate(final QuestionsEntityDto dto) {
        return new QuestionsEntity(dto.getSequenceNumber(), dto.getCreativeSource(),dto.getQuestion(),dto.getCategory(),dto.getMaxPoints(),dto.getAnswer1(),
        dto.getAnswer1Points(),dto.getAnswer2(),dto.getAnswer2Points(),dto.getAnswer3(),dto.getAnswer3Points(),
        dto.getAnswer4(),dto.getAnswer4Points(),dto.getAnswer5(),dto.getAnswer5Points(),dto.getAnswer6(),
        dto.getAnswer6Points());
    }
}
