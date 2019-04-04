package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
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

    // find for authentication by using UserDtoEntity from POST
    //public UserEntityDto getUserEntity(final UserEntityDto userEntityDto) {
    //    return userEntityDtoTransformer.generate(userEntityRepository.findOneByUserNameAndPassword(userEntityDto.getUserName(), userEntityDto.getPassword()));
    //}

    // get by Username AND Password
    public UserEntityDto getUserEntity(final String userName, final String password) {
        return userEntityDtoTransformer.generate(userEntityRepository.findOneByUserNameAndPassword(userName, password));
    }

    // get by Username only
    public UserEntityDto getUserEntity(final String userName) {
        return userEntityDtoTransformer.generate(userEntityRepository.findOneByUserName(userName));
    }

    public UserEntityDto createUserEntity(final UserEntityDto userEntityDto) {
        UserEntity userEntity = userEntityRepository.saveAndFlush(userEntityDtoTransformer.generate(userEntityDto));
        return userEntityDtoTransformer.generate(userEntity);
    }
}
