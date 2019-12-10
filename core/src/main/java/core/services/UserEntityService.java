package core.services;

import core.transformers.UserEntityDtoTransformer;
import db.entity.FriendshipsEntity;
import db.entity.UserEntity;
import db.repository.UserRepositoryDAO;
import model.UserEntityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;

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

    // GET - General
    public UserEntityDto getUserEntity(final String userName) {
        return userEntityDtoTransformer.generate(userEntityRepository.findOneByUserName(userName));
    }

    // GET - without removed friends
    public UserEntityDto getUserEntityWithoutRemovedFriends(final String userName) {
        UserEntityDto foundUser = userEntityDtoTransformer.generate(userEntityRepository.findOneByUserName(userName));
        Set<FriendshipsEntity> foundFriendshipsEntities = foundUser.getFriendsSet();
        foundFriendshipsEntities.removeIf(i -> i.getConnectionStatus().equals("removed"));
        foundUser.setFriendsSet(foundFriendshipsEntities);
        return foundUser;
    }

    // GET - with removed friends only
    public UserEntityDto getUserEntityRemovedFriendsOnly(final String userName) {
        UserEntityDto foundUser = userEntityDtoTransformer.generate(userEntityRepository.findOneByUserName(userName));
        Set<FriendshipsEntity> foundFriendshipsEntities = foundUser.getFriendsSet();
        foundFriendshipsEntities.removeIf(i -> !i.getConnectionStatus().equals("removed"));
        foundUser.setFriendsSet(foundFriendshipsEntities);
        return foundUser;
    }

    // GET (for alerts list of recent friendships invitations).
    public UserEntityDto getUserEntityRecentFriends(final String userName) {
        LocalDate windowDate = LocalDate.now().minusDays(14);
        UserEntityDto foundUser = userEntityDtoTransformer.generate(userEntityRepository.findOneByUserName(userName));
        Set<FriendshipsEntity> foundFriendshipsEntities = foundUser.getFriendsSet();
        foundFriendshipsEntities.removeIf(i -> !i.getConnectionStatus().equals("pending"));
        foundFriendshipsEntities.removeIf(i -> windowDate.isAfter(i.getCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
        foundUser.setFriendsSet(foundFriendshipsEntities);
        return foundUser;
    }

    // POST a new user
    public UserEntityDto createUserEntity(final UserEntityDto userEntityDto) {
        userEntityDto.setPublicProfile("Private");
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

    // PATCH password update
    public UserEntityDto patchPasswordUserEntity(final String userName, final String password, final String newPassword) {
        UserEntity userEntity = userEntityRepository.findOneByUserNameAndPassword(userName, password);
        userEntity.setPassword(newPassword);
        userEntityRepository.save(userEntity);
        return userEntityDtoTransformer.generate(userEntity);
    }
}
