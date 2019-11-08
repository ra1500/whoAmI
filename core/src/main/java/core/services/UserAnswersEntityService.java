package core.services;

import core.transformers.UserAnswersEntityDtoTransformer;
import db.entity.QuestionSetVersionEntity;
import db.entity.QuestionsEntity;
import db.entity.UserAnswersEntity;
import db.repository.QuestionSetVersionRepositoryDAO;
import db.repository.QuestionsRepositoryDAO;
import db.repository.UserAnswersRepositoryDAO;
import model.UserAnswersEntityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserAnswersEntityService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserAnswersRepositoryDAO userAnswersEntityRepository;
    private final QuestionsRepositoryDAO questionsRepositoryDAO;
    private final UserAnswersEntityDtoTransformer userAnswersEntityDtoTransformer;

    public UserAnswersEntityService(final UserAnswersRepositoryDAO userAnswersEntityRepository,
                                    final UserAnswersEntityDtoTransformer userAnswersEntityDtoTransformer,
                                    final QuestionsRepositoryDAO questionsRepositoryDAO) {
        this.userAnswersEntityRepository = userAnswersEntityRepository;
        this.userAnswersEntityDtoTransformer = userAnswersEntityDtoTransformer;
        this.questionsRepositoryDAO = questionsRepositoryDAO;
    }

    // GET an answer
    public UserAnswersEntityDto getUserAnswersEntity(final String userName, final String auditee, final Long questionsEntityId) {
        return userAnswersEntityDtoTransformer.generate(userAnswersEntityRepository.findOneByUserNameAndAuditeeAndQuestionsEntityId(userName, auditee, questionsEntityId));
    }

    // POST/PATCH a user's answer. If not found, POST, else if found, PATCH.
    public UserAnswersEntityDto createUserAnswersEntity(final UserAnswersEntityDto userAnswersEntityDto, final Long questionsEntityId) {

        // get userAnswersEntityDto from db, if it exists.
        UserAnswersEntity userAnswersEntity = userAnswersEntityRepository.findOneByUserNameAndAuditeeAndQuestionsEntityId(userAnswersEntityDto.getUserName(), userAnswersEntityDto.getAuditee(), questionsEntityId);

        if (userAnswersEntity == null) {

            // create a new 'raw' UserAnswersEntity based on the incoming UserAnswersDto (before adding two parents)
            UserAnswersEntity newUserAnswersEntity = userAnswersEntityDtoTransformer.generate(userAnswersEntityDto);

            // find and add the parent questionsEntity (ManyToOne only. no OneToMany, so no need to add child to a parent Set/List)
            QuestionsEntity foundQuestionsEntity = questionsRepositoryDAO.findOneById(questionsEntityId);
            newUserAnswersEntity.setQuestionsEntity(foundQuestionsEntity);

            // find and add the parent questionSetVersionEntity (ManyToOne only. no OneToMany, so no need to add child to a parent Set/List)
            QuestionSetVersionEntity foundQuestionSetVersionEntity = foundQuestionsEntity.getQuestionSetVersionEntity();
            newUserAnswersEntity.setQuestionSetVersionEntity(foundQuestionSetVersionEntity);

            // save the completed userAnswersEntity
            userAnswersEntityRepository.saveAndFlush(newUserAnswersEntity);

            return userAnswersEntityDtoTransformer.generate(newUserAnswersEntity);


        }
        else {
            // this else statement is an update/patch. no need to update parents since client already 'knows' who they are. and they are already set.
            userAnswersEntity.setUserName(userAnswersEntityDto.getUserName());
            userAnswersEntity.setAnswer(userAnswersEntityDto.getAnswer());
            userAnswersEntity.setAnswerPoints(userAnswersEntityDto.getAnswerPoints());
            userAnswersEntity.setAuditee(userAnswersEntityDto.getAuditee());
            userAnswersEntity.setComments(userAnswersEntityDto.getComments());
            userAnswersEntityRepository.save(userAnswersEntity);
            return userAnswersEntityDtoTransformer.generate(userAnswersEntity);
        }
    }

    public String deleteAllAnswersForUserNameAndAuditeeAndQuestionSetVersionEntityId(String user, String auditee, Long questionSetVersionEntityId) {
        userAnswersEntityRepository.deleteAllByUserNameAndAuditeeAndQuestionSetVersionEntityId(user, auditee, questionSetVersionEntityId);
        String allDeleted = "{ all answers deleted for this set }";
        return allDeleted;
    }

}
