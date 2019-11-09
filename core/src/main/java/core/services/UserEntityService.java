package core.services;

import core.transformers.UserEntityDtoTransformer;
import db.entity.UserEntity;
import db.repository.UserRepositoryDAO;
import model.UserEntityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserEntityService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserRepositoryDAO userEntityRepository;
    private final UserEntityDtoTransformer userEntityDtoTransformer;

    public UserEntityService(final UserRepositoryDAO userEntityRepository, final UserEntityDtoTransformer userEntityDtoTransformer) {
        this.userEntityRepository = userEntityRepository;
        this.userEntityDtoTransformer = userEntityDtoTransformer;
    }

    // GET
    public UserEntityDto getUserEntity(final String userName, final String password) {
        return userEntityDtoTransformer.generate(userEntityRepository.findOneByUserNameAndPassword(userName, password));
    }

    // GET
    public UserEntityDto getUserEntity(final String userName) {
        return userEntityDtoTransformer.generate(userEntityRepository.findOneByUserName(userName));
    }

    // POST
    public UserEntityDto createUserEntity(final UserEntityDto userEntityDto) {
        UserEntity userEntity = userEntityRepository.saveAndFlush(userEntityDtoTransformer.generate(userEntityDto));
        return userEntityDtoTransformer.generate(userEntity);
    }

    // PATCH
    public UserEntityDto patchUserEntity(final UserEntityDto userEntityDto) {
        UserEntity userEntity = userEntityRepository.findOneByUserName(userEntityDto.getUserName());
        userEntity.setPublicProfile(userEntityDto.getPublicProfile());
        userEntityRepository.save(userEntity);
        return userEntityDtoTransformer.generate(userEntity);
    }
}
