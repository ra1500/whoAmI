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
import java.util.Set;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

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

    // GET a question (lazy load, no parent)
    public QuestionsEntityDto getQuestionsEntity(final Long questionSetVersionEntityId, Long sequenceNumber) {
        // TODO validate that user has permission to the qsets that these questions belong to.
        return questionsEntityDtoTransformer.generate(questionsEntityRepository.findOneBySequenceNumberAndQuestionSetVersionEntityId(questionSetVersionEntityId, sequenceNumber));
    }

    // GET a question (eager load, with parent)
    public QuestionsEntityDto getQuestionsEntityWithParent(final Long questionSetVersionEntityId) {
        return questionsEntityDtoTransformer.generateEAGER(questionsEntityRepository.findOneByQuestionSetVersionEntityId(questionSetVersionEntityId));
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

    // DELETE a question
    public Integer deleteQuestionsEntity(final QuestionsEntityDto questionsEntityDto) {

         // find the question
         QuestionsEntity foundQuestionsEntity = questionsEntityRepository.findOneById(questionsEntityDto.getId());

         // user validation
         if (!foundQuestionsEntity.getCreativeSource().equals(questionsEntityDto.getCreativeSource())) { return new Integer(0);};

        // get info
        Long sequenceNumber = foundQuestionsEntity.getSequenceNumber();
        Long questionSetVersionEntityId = foundQuestionsEntity.getQuestionSetVersionEntity().getId();

        // delete the question
        Integer deleted = questionsEntityRepository.deleteOneById(questionsEntityDto.getId());

        // change all sequence numbers to accommodate delete (subtract 1 from sequence number for those that are after this question)
        Set<QuestionsEntity> questionsEntities = questionsEntityRepository.findStuff(questionSetVersionEntityId);
        questionsEntities.removeIf(i -> i.getSequenceNumber() < sequenceNumber);

        for (QuestionsEntity x : questionsEntities) {
            x.setSequenceNumber(x.getSequenceNumber()-1);
            questionsEntityRepository.save(x);
        }

        return deleted;
    }

}
