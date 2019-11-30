package core.services;

import core.transformers.QuestionSetVersionEntityDtoTransformer;
import db.entity.PermissionsEntity;
import db.entity.QuestionSetVersionEntity;
import db.entity.QuestionsEntity;
import db.repository.PermissionsRepositoryDAO;
import db.repository.QuestionSetVersionRepositoryDAO;
import db.repository.QuestionsRepositoryDAO;
import model.QuestionSetVersionEntityDto;
import model.QuestionsEntityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class QuestionSetVersionEntityService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final QuestionSetVersionRepositoryDAO questionSetVersionEntityRepository;
    private final PermissionsRepositoryDAO permissionsRepositoryDAO;
    private final QuestionSetVersionEntityDtoTransformer questionSetVersionEntityDtoTransformer;
    private final QuestionsRepositoryDAO questionsRepositoryDAO; // used in posting a new Qset by user with an inital blank question.

    public QuestionSetVersionEntityService(final QuestionSetVersionRepositoryDAO questionSetVersionEntityRepository,
                                           final QuestionSetVersionEntityDtoTransformer questionSetVersionEntityDtoTransformer,
                                            final PermissionsRepositoryDAO permissionsRepositoryDAO,
                                           final QuestionsRepositoryDAO questionsRepositoryDAO) {
        this.questionSetVersionEntityRepository = questionSetVersionEntityRepository;
        this.questionSetVersionEntityDtoTransformer = questionSetVersionEntityDtoTransformer;
        this.permissionsRepositoryDAO = permissionsRepositoryDAO;
        this.questionsRepositoryDAO = questionsRepositoryDAO;
    }

    // GET
    public QuestionSetVersionEntityDto getQuestionSetVersionEntity(final Long questionSetVersionEntityId) {
        return questionSetVersionEntityDtoTransformer.generate(questionSetVersionEntityRepository.findOneById(questionSetVersionEntityId));
    }

    // POST/PATCH
    public QuestionSetVersionEntityDto createQuestionSetVersionEntity(final QuestionSetVersionEntityDto questionSetVersionEntityDto,
                final Long questionSetVersionEntityId, final String userName) {

        // get questionSetVersionEntity from db, if it exists.
        QuestionSetVersionEntity questionSetVersionEntity = questionSetVersionEntityRepository.findOneById(questionSetVersionEntityId);

        if (questionSetVersionEntity == null) {

            // create and save new questionSetVersionEntity
            questionSetVersionEntityDto.setBadges(new Long(2)); // defaults to '2'. Company qsets should change this to '1'.
            QuestionSetVersionEntity newQuestionSetVersionEntity = questionSetVersionEntityRepository.saveAndFlush(questionSetVersionEntityDtoTransformer.generate(questionSetVersionEntityDto));

            // create and save a new blank questionsEntity (needed since front-end calls a questionsEntity in order to pull a parent Qset for use in Qset editing, so if there is no questionsEntity there is an error).
            QuestionsEntity newQuestionsEntity = new QuestionsEntity(new Long(1), userName);
            newQuestionsEntity.setQuestionSetVersionEntity(newQuestionSetVersionEntity);
            questionsRepositoryDAO.saveAndFlush(newQuestionsEntity);

            // create and save a new initial child permission. (ManyToOne. a permission only has one qset parent. a qset can have many permissions associated with it)
            PermissionsEntity newPermissionsEntity = new PermissionsEntity(userName, userName, new String("self"), new String("viewQuestionSet"), new Long(3), new Long(0), new String(""), new Long(0));
            newPermissionsEntity.setQuestionSetVersionEntity(newQuestionSetVersionEntity);
            permissionsRepositoryDAO.saveAndFlush(newPermissionsEntity);

            return questionSetVersionEntityDtoTransformer.generate(newQuestionSetVersionEntity);
        }
        else {
            // validate that user and creator are the same (to allow edits to this questionSet).
            if (!questionSetVersionEntity.getCreativeSource().equals(userName)) {
                return questionSetVersionEntityDto;}

            //questionSetVersionEntity.setId(questionSetVersionEntityDto.getId());
            //questionSetVersionEntity.setCreated(questionSetVersionEntityDto.getCreated());
            questionSetVersionEntity.setTitle(questionSetVersionEntityDto.getTitle());
            questionSetVersionEntity.setCategory(questionSetVersionEntityDto.getCategory());
            questionSetVersionEntity.setDescription(questionSetVersionEntityDto.getDescription());
            questionSetVersionEntity.setVersion(questionSetVersionEntityDto.getVersion());  // no need to allow changes to version. currently controlled by administrator at the mysql Database
            //questionSetVersionEntity.setCreativeSource(questionSetVersionEntityDto.getCreativeSource()); no need to allow changes to creator
            questionSetVersionEntity.setScoringStyle(questionSetVersionEntityDto.getScoringStyle());
            // questionSetVersionEntity.setBadges(questionSetVersionEntityDto.getBadges()); default is '2' and stays at '2'
            questionSetVersionEntity.setResult1(questionSetVersionEntityDto.getResult1()); // update
            questionSetVersionEntity.setResult2(questionSetVersionEntityDto.getResult2()); // update
            questionSetVersionEntity.setResult3(questionSetVersionEntityDto.getResult3()); // update
            questionSetVersionEntity.setResult4(questionSetVersionEntityDto.getResult4()); // update
            questionSetVersionEntity.setResult1start(questionSetVersionEntityDto.getResult1start()); // update
            questionSetVersionEntity.setResult2start(questionSetVersionEntityDto.getResult2start()); // update
            questionSetVersionEntity.setResult3start(questionSetVersionEntityDto.getResult3start()); // update
            questionSetVersionEntityRepository.save(questionSetVersionEntity);
            return questionSetVersionEntityDtoTransformer.generate(questionSetVersionEntity);
        }

    }
}
