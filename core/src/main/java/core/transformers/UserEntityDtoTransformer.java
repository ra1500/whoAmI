package core.transformers;

import db.entity.FriendshipsEntity;
import db.entity.UserEntity;
import db.repository.FriendshipsRepositoryDAO;
import model.UserEntityDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserEntityDtoTransformer {

    private FriendshipsEntityDtoTransformer friendshipsEntityDtoTransformer;

    // used in the 'mini' getUserEntity service below
    private final FriendshipsRepositoryDAO friendshipsRepositoryDAO;

    public UserEntityDtoTransformer(final FriendshipsEntityDtoTransformer friendshipsEntityDtoTransformer, FriendshipsRepositoryDAO friendshipsRepositoryDAO) {
        this.friendshipsEntityDtoTransformer = friendshipsEntityDtoTransformer;
        this.friendshipsRepositoryDAO = friendshipsRepositoryDAO;
    }

    // this is a 'mini' getFriendshipsEntities service
    private Set<FriendshipsEntity> getFriendshipsSetService(UserEntity userEntity) {
        Set<FriendshipsEntity> friendshipsSet = friendshipsRepositoryDAO.findAllByUserEntity(userEntity);
        return friendshipsSet;
    }

    // GET
    public UserEntityDto generate(final UserEntity userEntity) {
        if (userEntity == null || userEntity.getId() == null) {
            return null;
        }

        UserEntityDto dto = new UserEntityDto();
        //dto.setId(userEntity.getId());
        //dto.setCreated(userEntity.getCreated());
        dto.setUserName(userEntity.getUserName());
        dto.setPassword(userEntity.getPassword());
        dto.setPublicProfile(userEntity.getPublicProfile());
        dto.setFriendsSet(getFriendshipsSetService(userEntity));

        //dto.setFriendsSet(userEntity.getFriendsSet()
        //            .stream()
        //            .map(friendshipsEntityDtoTransformer::generate)
        //            .sorted()
        //            .collect(Collectors.toList()));


        return dto;
    }

    // POST
    public UserEntity generate(final UserEntityDto dto) {
        return new UserEntity(dto.getUserName(),dto.getPassword());
    }

    // PATCH to the db an update
    public UserEntity patch(final UserEntity dto) {
        return new UserEntity(dto.getUserName(), dto.getPassword());
    }
}
