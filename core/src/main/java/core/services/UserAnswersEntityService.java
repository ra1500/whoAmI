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

    public UserAnswersEntityDto getUserAnswersEntity(final String userName, final Long questionId) {
        return userAnswersEntityDtoTransformer.generate(userAnswersEntityRepository.findOneByUserNameAndQuestionId(userName, questionId));
    }

    // not currently used method
    public UserAnswersEntityDto createUserAnswersEntity2(final UserAnswersEntityDto userAnswersEntityDto) {
        UserAnswersEntity userAnswersEntity = userAnswersEntityRepository.saveAndFlush(userAnswersEntityDtoTransformer.generate(userAnswersEntityDto));
        return userAnswersEntityDtoTransformer.generate(userAnswersEntity);
    }

    public UserAnswersEntityDto createUserAnswersEntity(final UserAnswersEntityDto userAnswersEntityDto) {

        String userName = userAnswersEntityDto.getUserName();
        Long questionId = userAnswersEntityDto.getQuestionId();

        if (getUserAnswersEntity(userName, questionId) != null) {
            userAnswersEntityRepository.deleteOneByUserNameAndQuestionId(userName, questionId);
        }

        UserAnswersEntity userAnswersEntity = userAnswersEntityRepository.saveAndFlush(userAnswersEntityDtoTransformer.generate(userAnswersEntityDto));
        return userAnswersEntityDtoTransformer.generate(userAnswersEntity);
    }
}
