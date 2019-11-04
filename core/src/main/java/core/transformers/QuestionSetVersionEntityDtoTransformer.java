package core.transformers;

import db.entity.QuestionSetVersionEntity;
import db.entity.QuestionsEntity;
import db.repository.QuestionsRepositoryDAO;
import model.QuestionSetVersionEntityDto;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class QuestionSetVersionEntityDtoTransformer {

    private QuestionsEntityDtoTransformer questionsEntityDtoTransformer;

    // used in the 'mini' getUserEntity service below
    private final QuestionsRepositoryDAO questionsRepositoryDAO;

    public QuestionSetVersionEntityDtoTransformer(final QuestionsEntityDtoTransformer questionsEntityDtoTransformer, QuestionsRepositoryDAO questionsRepositoryDAO) {
        this.questionsEntityDtoTransformer = questionsEntityDtoTransformer;
        this.questionsRepositoryDAO = questionsRepositoryDAO;
    }

    // this is a 'mini' getQuestionsEntities service
    private Set<QuestionsEntity> getQuestionsSetService(QuestionSetVersionEntity questionSetVersionEntity) {
        Set<QuestionsEntity> questionsSet = questionsRepositoryDAO.findAllByQuestionSetVersionEntity(questionSetVersionEntity);
        return questionsSet;
    }

    // GET
    public QuestionSetVersionEntityDto generate(final QuestionSetVersionEntity questionSetVersionEntity) {
        if (questionSetVersionEntity == null || questionSetVersionEntity.getGid() == null) {
            return null;
        }

        QuestionSetVersionEntityDto dto = new QuestionSetVersionEntityDto();
        //dto.setGid(questionSetVersionEntity.getGid());
        dto.setQuestionSetVersion(questionSetVersionEntity.getQuestionSetVersion());
        //dto.setCreated(questionSetVersionEntity.getCreated());
        //dto.setQuestionsSet(getQuestionsSetService(questionSetVersionEntity));
        dto.setTitle(questionSetVersionEntity.getTitle());
        dto.setVersion(questionSetVersionEntity.getVersion());
        dto.setCategory(questionSetVersionEntity.getCategory());
        dto.setDescription(questionSetVersionEntity.getDescription());
        dto.setCreativeSource(questionSetVersionEntity.getCreativeSource());

        return dto;
    }

    // POST
    public QuestionSetVersionEntity generate(final QuestionSetVersionEntityDto dto) {
        return new QuestionSetVersionEntity(dto.getGid());
    }
}
