package core.services;

import core.transformers.QuestionsEntityDtoTransformer;
import db.entity.QuestionSetVersionEntity;
import db.entity.QuestionsEntity;
import db.repository.QuestionSetVersionRepositoryDAO;
import db.repository.QuestionsRepositoryDAO;
import model.QuestionsEntityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class QuestionsEntityService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final QuestionsRepositoryDAO questionsEntityRepository;
    private final QuestionSetVersionRepositoryDAO questionSetVersionRepositoryDAO;

    private final QuestionsEntityDtoTransformer questionsEntityDtoTransformer;

    public QuestionsEntityService(final QuestionsRepositoryDAO questionsEntityRepository, final QuestionsEntityDtoTransformer questionsEntityDtoTransformer,
                                  final QuestionSetVersionRepositoryDAO questionSetVersionRepositoryDAO) {
        this.questionsEntityRepository = questionsEntityRepository;
        this.questionsEntityDtoTransformer = questionsEntityDtoTransformer;
        this.questionSetVersionRepositoryDAO = questionSetVersionRepositoryDAO;
    }

    // GET a question
    public QuestionsEntityDto getQuestionsEntity(final QuestionSetVersionEntity questionSetVersionEntity, Long sequenceNumber) {
        return questionsEntityDtoTransformer.generate(questionsEntityRepository.findOneByQuestionSetVersionEntityAndSequenceNumber(questionSetVersionEntity, sequenceNumber));
    }

    // POST
    public QuestionsEntityDto createQuestionsEntity(final QuestionsEntityDto questionsEntityDto) {
        // get questionSetVersionEntity related to this question.
        QuestionSetVersionEntity foundQuestionSetVersionEntity = questionSetVersionRepositoryDAO.findOneByQuestionSetVersion(questionsEntityDto.getQuestionSetVersion());

        // check if this question already exists in db
        QuestionsEntity questionsEntity = questionsEntityRepository.findOneByQuestionSetVersionEntityAndSequenceNumber(foundQuestionSetVersionEntity,questionsEntityDto.getSequenceNumber());

        if (questionsEntity == null) {
            // add the found QsetVersion to the questionEntity, then save
            questionsEntityDto.setQuestionSetVersionEntity(foundQuestionSetVersionEntity);
            QuestionsEntity newQuestionsEntity = questionsEntityRepository.saveAndFlush(questionsEntityDtoTransformer.generate(questionsEntityDto));
            return questionsEntityDtoTransformer.generate(newQuestionsEntity);
        }
        else {
            questionsEntity.setQuestion(questionsEntityDto.getQuestion());
            questionsEntity.setMaxPoints(questionsEntityDto.getMaxPoints());
            questionsEntity.setCreativeSource(questionsEntityDto.getCreativeSource());
            questionsEntity.setAnswer1(questionsEntityDto.getAnswer1());
            questionsEntity.setAnswer1Points(questionsEntityDto.getAnswer1Points());
            questionsEntity.setAnswer2(questionsEntityDto.getAnswer2());
            questionsEntity.setAnswer2Points(questionsEntityDto.getAnswer2Points());
            questionsEntity.setAnswer3(questionsEntityDto.getAnswer3());
            questionsEntity.setAnswer3Points(questionsEntityDto.getAnswer3Points());
            questionsEntity.setAnswer4(questionsEntityDto.getAnswer4());
            questionsEntity.setAnswer4Points(questionsEntityDto.getAnswer4Points());
            questionsEntity.setAnswer5(questionsEntityDto.getAnswer5());
            questionsEntity.setAnswer5Points(questionsEntityDto.getAnswer5Points());
            questionsEntity.setAnswer6(questionsEntityDto.getAnswer6());
            questionsEntity.setAnswer6Points(questionsEntityDto.getAnswer6Points());
            return questionsEntityDtoTransformer.generate(questionsEntity);
        }
    }
}
