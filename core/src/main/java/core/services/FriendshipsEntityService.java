package core.services;

import core.transformers.FriendshipsEntityDtoTransformer;
import db.entity.FriendshipsEntity;
import db.entity.UserEntity;
import db.repository.FriendshipsRepositoryDAO;
import model.FriendshipsEntityDto;
import model.FriendshipsEntityDtoPOST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FriendshipsEntityService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final FriendshipsRepositoryDAO friendshipsRepositoryDAO;
    private final FriendshipsEntityDtoTransformer friendshipsEntityDtoTransformer;

    public FriendshipsEntityService(final FriendshipsRepositoryDAO friendshipsRepositoryDAO, final FriendshipsEntityDtoTransformer friendshipsEntityDtoTransformer) {
        this.friendshipsRepositoryDAO = friendshipsRepositoryDAO;
        this.friendshipsEntityDtoTransformer = friendshipsEntityDtoTransformer;
    }

    // for GET.
    public FriendshipsEntityDto getFriendshipsEntity(final UserEntity userEntity, final String friend) {
        return friendshipsEntityDtoTransformer.generate(friendshipsRepositoryDAO.findOneByUserEntityAndFriend(userEntity, friend));
    }

    // for POST
    public FriendshipsEntityDto createFriendshipsEntity(final FriendshipsEntityDtoPOST friendshipsEntityDtoPOST) {
        FriendshipsEntity friendshipsEntity = friendshipsRepositoryDAO.saveAndFlush(friendshipsEntityDtoTransformer.generate(friendshipsEntityDtoPOST));
        return friendshipsEntityDtoTransformer.generate(friendshipsEntity);
    }

    // for PATCH  // TODO: is this just returning the same object to the client as the client sent in instead of coming from the updated db?
    public FriendshipsEntityDto patchFriendshipsEntity(final FriendshipsEntityDtoPOST friendshipsEntityDtoPOST) {
        FriendshipsEntity friendshipsEntity = friendshipsRepositoryDAO.findOneByGid(friendshipsEntityDtoPOST.getGid());
        friendshipsEntity.setConnectionType(friendshipsEntityDtoPOST.getConnectionType());
        friendshipsEntity.setConnectionStatus(friendshipsEntityDtoPOST.getConnectionStatus());
        friendshipsEntity.setVisibilityPermission(friendshipsEntityDtoPOST.getVisibilityPermission());
        friendshipsRepositoryDAO.save(friendshipsEntity);
        return friendshipsEntityDtoTransformer.generate(friendshipsEntity);
    }

    // for DELETE.
    public Integer deleteFriendshipsEntity(final Long gid) {
        Integer deletedFriendshipsEntity = friendshipsRepositoryDAO.deleteOneByGid(gid);
        return deletedFriendshipsEntity;
    }
}
