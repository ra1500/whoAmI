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

    // used in the 'mini' getUserEntity service below
    private final UserRepositoryDAO userRepositoryDAO;

    public FriendshipsEntityDtoTransformer(UserRepositoryDAO userRepositoryDAO) {
        this.userRepositoryDAO = userRepositoryDAO;
    }

    // this is a 'mini' getUserEntity service... not needed?
    //private UserEntity getUserEntityService(FriendshipsEntity friendshipsEntity) {
    //    UserEntity userEntity = userRepositoryDAO.findOneByUserName(friendshipsEntity.getUserEntity().getUserName());
    //    return userEntity;
    //}

    // GET from db
    public FriendshipsEntityDto generate(final FriendshipsEntity friendshipsEntity) {
        if (friendshipsEntity == null || friendshipsEntity.getGid() == null) {
            return null;
        }

        FriendshipsEntityDto dto = new FriendshipsEntityDto();
        dto.setGid(friendshipsEntity.getGid());
        //dto.setCreated(friendshipsEntity.getCreated()); // no need to send out created date now
        //dto.setUserEntity(getUserEntityService(friendshipsEntity)); // not needed?
        dto.setInviter(friendshipsEntity.getInviter());
        dto.setFriend(friendshipsEntity.getFriend());
        dto.setStatus(friendshipsEntity.getStatus());
        dto.setConnectionType(friendshipsEntity.getConnectionType());
        dto.setVisibilityPermission(friendshipsEntity.getVisibilityPermission());

        return dto;
    }

    // POST to the db a new friendship
    public FriendshipsEntity generate(final FriendshipsEntityDtoPOST dto) {
        UserEntity userEntity = userRepositoryDAO.findOneByUserName(dto.getUserName());
        return new FriendshipsEntity(
                userEntity,
                dto.getInviter(),dto.getFriend(), dto.getStatus(),
                dto.getConnectionType(),dto.getVisibilityPermission());
    }
}
