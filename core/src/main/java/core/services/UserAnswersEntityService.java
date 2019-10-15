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

    public UserAnswersEntityDto getUserAnswersEntity(final Long userId, final Long questionId) {
        return userAnswersEntityDtoTransformer.generate(userAnswersEntityRepository.findOneByUserIdAndQuestionId(userId, questionId));
    }

    // not currently used method
    public UserAnswersEntityDto createUserAnswersEntity2(final UserAnswersEntityDto userAnswersEntityDto) {
        UserAnswersEntity userAnswersEntity = userAnswersEntityRepository.saveAndFlush(userAnswersEntityDtoTransformer.generate(userAnswersEntityDto));
        return userAnswersEntityDtoTransformer.generate(userAnswersEntity);
    }

    public UserAnswersEntityDto createUserAnswersEntity(final UserAnswersEntityDto userAnswersEntityDto) {

        Long userId = userAnswersEntityDto.getUserId();
        Long questionId = userAnswersEntityDto.getQuestionId();

        if (getUserAnswersEntity(userId, questionId) != null) {
            userAnswersEntityRepository.deleteOneByUserIdAndQuestionId(userId, questionId);
        }

        UserAnswersEntity userAnswersEntity = userAnswersEntityRepository.saveAndFlush(userAnswersEntityDtoTransformer.generate(userAnswersEntityDto));
        return userAnswersEntityDtoTransformer.generate(userAnswersEntity);
    }
}
