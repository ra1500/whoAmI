package core.transformers;

import db.entity.FriendshipsEntity;
import db.entity.UserEntity;
import db.repository.UserRepositoryDAO;
import model.FriendshipsEntityDto;
import model.FriendshipsEntityDtoPOST;
import model.UserEntityDto;
import org.springframework.stereotype.Component;

@Component
public class FriendshipsEntityDtoTransformer {

    //private UserEntityDtoTransformer userEntityDtoTransformer; // used to setUserEntity when an outgoing DTO to client is made here.
    private UserRepositoryDAO userRepositoryDAO;

    public FriendshipsEntityDtoTransformer(UserRepositoryDAO userRepositoryDAO) {
        this.userRepositoryDAO = userRepositoryDAO;
        //this.userEntityDtoTransformer = userEntityDtoTransformer;
    }

    // GET from db
    public FriendshipsEntityDto generate(final FriendshipsEntity friendshipsEntity) {
        if (friendshipsEntity == null || friendshipsEntity.getGid() == null) {
            return null;
        }

        FriendshipsEntityDto dto = new FriendshipsEntityDto();
        dto.setGid(friendshipsEntity.getGid());
        dto.setCreated(friendshipsEntity.getCreated());

        //
        //userEntityDtoTransformer.generate(friendshipsEntity.getUserEntity());
        //dto.setUserEntity(userEntityDtoTransformer.generate(friendshipsEntity.getUserEntity()));

        //dto.setUserEntity(friendshipsEntity.getUserEntity());
        dto.setInviter(friendshipsEntity.getInviter());
        dto.setFriend(friendshipsEntity.getFriend());
        dto.setStatus(friendshipsEntity.getStatus());
        dto.setConnectionType(friendshipsEntity.getConnectionType());
        dto.setVisibilityPermission(friendshipsEntity.getVisibilityPermission());

        return dto;
    }

    // POST to the db a new one
    public FriendshipsEntity generate(final FriendshipsEntityDtoPOST dto) {
        UserEntity userEntity = userRepositoryDAO.findOneByUserName(dto.getUserName()); // works ok??
        return new FriendshipsEntity(
                userEntity,
                dto.getInviter(),dto.getFriend(), dto.getStatus(),
                dto.getConnectionType(),dto.getVisibilityPermission());
    }
}
