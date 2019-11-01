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

    // POST a user's answer (updates as well manually by deleting) TODO: make this an update with save(), not delete and replace.
    public UserAnswersEntityDto createUserAnswersEntity(final UserAnswersEntityDto userAnswersEntityDto) {

        String userName = userAnswersEntityDto.getUserName();
        String auditee = userAnswersEntityDto.getAuditee();
        Long questionId = userAnswersEntityDto.getQuestionId();
        Long questionSetVersion = userAnswersEntityDto.getQuestionSetVersion();

        if (getUserAnswersEntity(userName, auditee, questionSetVersion, questionId) != null) {
            userAnswersEntityRepository.deleteOneByUserNameAndAuditeeAndQuestionSetVersionAndQuestionId(userName, auditee, questionSetVersion, questionId);
        }

        UserAnswersEntity userAnswersEntity = userAnswersEntityRepository.saveAndFlush(userAnswersEntityDtoTransformer.generate(userAnswersEntityDto));
        return userAnswersEntityDtoTransformer.generate(userAnswersEntity);
    }
}
