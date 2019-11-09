package core.transformers;

import db.entity.FriendshipsEntity;
import db.entity.UserEntity;
import db.repository.UserRepositoryDAO;
import model.FriendshipsEntityDto;
import model.UserEntityDto;
import org.springframework.stereotype.Component;

@Component
public class FriendshipsEntityDtoTransformer {

    public FriendshipsEntityDtoTransformer() {
    }

    // GET from db
    public FriendshipsEntityDto generate(final FriendshipsEntity friendshipsEntity) {
        if (friendshipsEntity == null || friendshipsEntity.getId() == null) {
            return null;
        }
        FriendshipsEntityDto dto = new FriendshipsEntityDto();
        dto.setId(friendshipsEntity.getId());
        dto.setCreated(friendshipsEntity.getCreated());
        dto.setInviter(friendshipsEntity.getInviter());
        dto.setFriend(friendshipsEntity.getFriend());
        dto.setConnectionStatus(friendshipsEntity.getConnectionStatus());
        dto.setConnectionType(friendshipsEntity.getConnectionType());
        dto.setVisibilityPermission(friendshipsEntity.getVisibilityPermission());
        return dto;
    }

    // POST to the db a new friendship
    public FriendshipsEntity generate(final FriendshipsEntityDto dto) {
        return new FriendshipsEntity(dto.getInviter(),dto.getFriend(), dto.getConnectionStatus(),
                dto.getConnectionType(),dto.getVisibilityPermission());
    }

    // PATCH to the db an update
    public FriendshipsEntity patch(final FriendshipsEntityDto dto) {
        return new FriendshipsEntity(dto.getInviter(),dto.getFriend(), dto.getConnectionStatus(),
                dto.getConnectionType(),dto.getVisibilityPermission());
    }
}
