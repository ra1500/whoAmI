package core.transformers;

import db.entity.FriendshipsEntity;
import db.entity.UserEntity;
import db.repository.FriendshipsRepositoryDAO;
import model.UserEntityDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
    private List<FriendshipsEntity> getFriendshipsListService(UserEntity userEntity) {
        List<FriendshipsEntity> friendshipsList = friendshipsRepositoryDAO.findAllByUserEntity(userEntity);
        return friendshipsList;
    }

    // GET
    public UserEntityDto generate(final UserEntity userEntity) {
        if (userEntity == null || userEntity.getGid() == null) {
            return null;
        }

        UserEntityDto dto = new UserEntityDto();
        dto.setGid(userEntity.getGid());
        dto.setCreated(userEntity.getCreated());
        dto.setUserName(userEntity.getUserName());
        dto.setPassword(userEntity.getPassword());
        dto.setFriendsList(getFriendshipsListService(userEntity));  // works?

        //dto.setFriendsList(userEntity.getFriendsList()
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
}
