package core.transformers;

import db.entity.UserEntity;
import model.UserEntityDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class UserEntityDtoTransformer {

    private FriendshipsEntityDtoTransformer friendshipsEntityDtoTransformer;

    public UserEntityDtoTransformer(final FriendshipsEntityDtoTransformer friendshipsEntityDtoTransformer) {
        this.friendshipsEntityDtoTransformer = friendshipsEntityDtoTransformer;
    }


    public UserEntityDto generate(final UserEntity userEntity) {
        if (userEntity == null || userEntity.getGid() == null) {
            return null;
        }

        UserEntityDto dto = new UserEntityDto();
        dto.setGid(userEntity.getGid());
        dto.setCreated(userEntity.getCreated());
        dto.setUserName(userEntity.getUserName());
        dto.setPassword(userEntity.getPassword());
        //dto.setFriendsList(userEntity.getFriendsList());
                   // .stream()
                   // .map(friendshipsEntityDtoTransformer::generate)
                   // .sorted()
                   // .collect(Collectors.toList()));


        return dto;
    }

    public UserEntity generate(final UserEntityDto dto) {
        return new UserEntity(dto.getUserName(),dto.getPassword());
    }
}
