package core.services;

import core.transformers.FriendshipsEntityDtoTransformer;
import db.entity.FriendshipsEntity;
import db.entity.UserEntity;
import db.repository.FriendshipsRepositoryDAO;
import db.repository.UserRepositoryDAO;
import model.FriendshipsEntityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FriendshipsEntityService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final FriendshipsRepositoryDAO friendshipsRepositoryDAO;
    private final UserRepositoryDAO userRepositoryDAO;
    private final FriendshipsEntityDtoTransformer friendshipsEntityDtoTransformer;

    public FriendshipsEntityService(final FriendshipsRepositoryDAO friendshipsRepositoryDAO, final FriendshipsEntityDtoTransformer friendshipsEntityDtoTransformer,
                                    UserRepositoryDAO userRepositoryDAO) {
        this.friendshipsRepositoryDAO = friendshipsRepositoryDAO;
        this.friendshipsEntityDtoTransformer = friendshipsEntityDtoTransformer;
        this.userRepositoryDAO = userRepositoryDAO;
    }

    // GET.
    public FriendshipsEntityDto getFriendshipsEntity(final String user, final Long friendId) {
        UserEntity userEntity = userRepositoryDAO.findOneByUserName(user);
        Long userEntityId = userEntity.getId();

        return friendshipsEntityDtoTransformer.generate(friendshipsRepositoryDAO.findOneByIdAndUserEntityId(friendId, userEntityId));
    }

    // POST/PATCH a friendship (double entry of friendships(qty 2) + double entry of adding parent to child, and child to Set in parent)
    public FriendshipsEntityDto createFriendshipsEntity(final FriendshipsEntityDto friendshipsEntityDto, final String userName) {

        // get friendshipsEntityDto from db, if it exists.
        UserEntity foundUserEntity = userRepositoryDAO.findOneByUserName(userName);
        Long userId = foundUserEntity.getId();
        FriendshipsEntity foundFriendshipsEntity = friendshipsRepositoryDAO.findOneByUserEntityIdAndId(userId, friendshipsEntityDto.getId());

        // get friend userEntity if it exists
        UserEntity friendExistsUserEntity = userRepositoryDAO.findOneByUserName(friendshipsEntityDto.getFriend());

        if (foundFriendshipsEntity == null && friendExistsUserEntity != null ) {

            // create a new 'raw' friendshipsEntity (1 of 2 entries) (ManyToOne twice, instead of ManyToMany)
            FriendshipsEntity newFriendshipsEntity1 = friendshipsEntityDtoTransformer.generate(friendshipsEntityDto);

            // add userEntity
            newFriendshipsEntity1.setUserEntity(foundUserEntity);

            // save completed friendshipsEntity1
            friendshipsRepositoryDAO.saveAndFlush(newFriendshipsEntity1);

            // create a new 'raw' friendshipsEntity (2 of 2 entries) (ManyToOne twice, instead of ManyToMany)
            FriendshipsEntityDto friendshipsEntityDto2 = friendshipsEntityDto;
            friendshipsEntityDto2.setFriend(userName);
            FriendshipsEntity newFriendshipsEntity2 = friendshipsEntityDtoTransformer.generate(friendshipsEntityDto2);

            // add userEntity (of friend)
            newFriendshipsEntity2.setUserEntity(friendExistsUserEntity);

            // save completed friendshipsEntity2
            friendshipsRepositoryDAO.saveAndFlush(newFriendshipsEntity2);

            // add both new friendships to their respective user's friendships Lists
            foundUserEntity.getFriendsSet().add(newFriendshipsEntity1);
            friendExistsUserEntity.getFriendsSet().add(newFriendshipsEntity2);

            // return only the 'main' 1st entry friendhsipsEntity
            return friendshipsEntityDtoTransformer.generate(newFriendshipsEntity1);
        }
        else if (foundFriendshipsEntity.getConnectionStatus().equals("pending") && !friendshipsEntityDto.getConnectionStatus().equals("removed"))
        {
            // first entry
            foundFriendshipsEntity.setConnectionStatus(friendshipsEntityDto.getConnectionStatus());
            friendshipsRepositoryDAO.save(foundFriendshipsEntity);

            // second entry (two-sided)
            String secondUser = foundFriendshipsEntity.getFriend();
            UserEntity secondUserEntity = userRepositoryDAO.findOneByUserName(secondUser);
            FriendshipsEntity secondFriendshipsEntity = friendshipsRepositoryDAO.findOneByUserEntityIdAndFriend(secondUserEntity.getId(), userName);
            secondFriendshipsEntity.setConnectionStatus("Connected");
            friendshipsRepositoryDAO.save(secondFriendshipsEntity);
            return friendshipsEntityDtoTransformer.generate(foundFriendshipsEntity);
        } // end else if
        else {
        // for updates except accepting an invitation. (therefore only one-sided here)
        foundFriendshipsEntity.setConnectionStatus(friendshipsEntityDto.getConnectionStatus());
        foundFriendshipsEntity.setConnectionType(friendshipsEntityDto.getConnectionType());
        foundFriendshipsEntity.setVisibilityPermission(friendshipsEntityDto.getVisibilityPermission());
        friendshipsRepositoryDAO.save(foundFriendshipsEntity);
        return friendshipsEntityDtoTransformer.generate(foundFriendshipsEntity);
        } // end else
    }

    // for DELETE.
    public Integer deleteFriendshipsEntity(final Long id) {
        Integer deletedFriendshipsEntity = friendshipsRepositoryDAO.deleteOneById(id);
        return deletedFriendshipsEntity;
    }
}
