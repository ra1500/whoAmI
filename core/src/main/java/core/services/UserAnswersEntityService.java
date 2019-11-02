package core.services;

import core.transformers.UserAnswersEntityDtoTransformer;
import db.entity.UserAnswersEntity;
import db.repository.UserAnswersRepositoryDAO;
import model.UserAnswersEntityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserAnswersEntityService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UserAnswersRepositoryDAO userAnswersEntityRepository;

    private final UserAnswersEntityDtoTransformer userAnswersEntityDtoTransformer;

    public UserAnswersEntityService(final UserAnswersRepositoryDAO userAnswersEntityRepository, final UserAnswersEntityDtoTransformer userAnswersEntityDtoTransformer) {
        this.userAnswersEntityRepository = userAnswersEntityRepository;
        this.userAnswersEntityDtoTransformer = userAnswersEntityDtoTransformer;
    }

    // GET an answer
    public UserAnswersEntityDto getUserAnswersEntity(final String userName, final String auditee, final Long questionSetVersion, final Long questionId) {
        return userAnswersEntityDtoTransformer.generate(userAnswersEntityRepository.findOneByUserNameAndAuditeeAndQuestionSetVersionAndQuestionId(userName, auditee, questionSetVersion, questionId));
    }

    // not currently used method
    public UserAnswersEntityDto createUserAnswersEntity2(final UserAnswersEntityDto userAnswersEntityDto) {
        UserAnswersEntity userAnswersEntity = userAnswersEntityRepository.saveAndFlush(userAnswersEntityDtoTransformer.generate(userAnswersEntityDto));
        return userAnswersEntityDtoTransformer.generate(userAnswersEntity);
    }

    // POST/PATCH a user's answer. If not found, POST, else if found, PATCH.
    public UserAnswersEntityDto createUserAnswersEntity(final UserAnswersEntityDto userAnswersEntityDto) {
        UserAnswersEntity userAnswersEntity = userAnswersEntityRepository.findOneByUserNameAndAuditeeAndQuestionSetVersionAndQuestionId(userAnswersEntityDto.getUserName(), userAnswersEntityDto.getAuditee(), userAnswersEntityDto.getQuestionSetVersion(), userAnswersEntityDto.getQuestionId());

        if (userAnswersEntity == null) {
            UserAnswersEntity newUserAnswersEntity = userAnswersEntityRepository.saveAndFlush(userAnswersEntityDtoTransformer.generate(userAnswersEntityDto));
            return userAnswersEntityDtoTransformer.generate(newUserAnswersEntity);
        }
        else {
            userAnswersEntity.setAuditee(userAnswersEntityDto.getAuditee());
            userAnswersEntity.setAnswer(userAnswersEntityDto.getAnswer());
            userAnswersEntity.setUserName(userAnswersEntityDto.getUserName());
            userAnswersEntity.setAnswerPoints(userAnswersEntityDto.getAnswerPoints());
            userAnswersEntity.setComments(userAnswersEntityDto.getComments());
            userAnswersEntity.setQuestionId(userAnswersEntityDto.getQuestionId());
            userAnswersEntity.setQuestionSetVersion(userAnswersEntityDto.getQuestionSetVersion());
            userAnswersEntityRepository.save(userAnswersEntity);
            return userAnswersEntityDtoTransformer.generate(userAnswersEntity);
        }
    }
}
