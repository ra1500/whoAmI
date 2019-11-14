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

import java.util.List;

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
    public QuestionsEntityDto getQuestionsEntity(final Long questionSetVersionEntityId, Long sequenceNumber) {
        return questionsEntityDtoTransformer.generate(questionsEntityRepository.findOneBySequenceNumberAndQuestionSetVersionEntityId(questionSetVersionEntityId, sequenceNumber));
    }

    // POST/PATCH
    public QuestionsEntityDto createQuestionsEntity(final QuestionsEntityDto questionsEntityDto, final Long questionSetVersionEntityId, final String userName) {

        // get questionsEntityDto from db, if it exists.
        QuestionsEntity foundQuestionsEntity = questionsEntityRepository.findOneBySequenceNumberAndQuestionSetVersionEntityId(questionsEntityDto.getSequenceNumber(), questionSetVersionEntityId);

        if (foundQuestionsEntity == null) {

            // create a 'raw' questionsEntity based on the incoming questionsEntityDto (before adding parent)
            QuestionsEntity newQuestionsEntity = questionsEntityDtoTransformer.generate(questionsEntityDto);

            // add the parent questionSetVersionEntity
            QuestionSetVersionEntity foundQuestionSetVersionEntity = questionSetVersionRepositoryDAO.findOneById(questionSetVersionEntityId);
            newQuestionsEntity.setQuestionSetVersionEntity(foundQuestionSetVersionEntity);

            // save the completed questionsEntity
            questionsEntityRepository.saveAndFlush(newQuestionsEntity);
            return questionsEntityDtoTransformer.generate(newQuestionsEntity);
        }

        else {
            // validate that user and creator are the same (to allow edits to this questionSet).
            if (!foundQuestionsEntity.getCreativeSource().equals(userName)) {
                return questionsEntityDto;}


            // this else statement is an update/patch. no need to update parent since client already 'knows' who they are. and they are already set.
            foundQuestionsEntity.setSequenceNumber(questionsEntityDto.getSequenceNumber());
            foundQuestionsEntity.setCreativeSource(questionsEntityDto.getCreativeSource());
            foundQuestionsEntity.setQuestion(questionsEntityDto.getQuestion());
            foundQuestionsEntity.setCategory(questionsEntityDto.getCategory());
            foundQuestionsEntity.setMaxPoints(questionsEntityDto.getMaxPoints());
            foundQuestionsEntity.setAnswer1(questionsEntityDto.getAnswer1());
            foundQuestionsEntity.setAnswer1Points(questionsEntityDto.getAnswer1Points());
            foundQuestionsEntity.setAnswer2(questionsEntityDto.getAnswer2());
            foundQuestionsEntity.setAnswer2Points(questionsEntityDto.getAnswer2Points());
            foundQuestionsEntity.setAnswer3(questionsEntityDto.getAnswer3());
            foundQuestionsEntity.setAnswer3Points(questionsEntityDto.getAnswer3Points());
            foundQuestionsEntity.setAnswer4(questionsEntityDto.getAnswer4());
            foundQuestionsEntity.setAnswer4Points(questionsEntityDto.getAnswer4Points());
            foundQuestionsEntity.setAnswer5(questionsEntityDto.getAnswer5());
            foundQuestionsEntity.setAnswer5Points(questionsEntityDto.getAnswer5Points());
            foundQuestionsEntity.setAnswer6(questionsEntityDto.getAnswer6());
            foundQuestionsEntity.setAnswer6Points(questionsEntityDto.getAnswer6Points());
            questionsEntityRepository.save(foundQuestionsEntity);
            return questionsEntityDtoTransformer.generate(foundQuestionsEntity);
        }
    }
}
