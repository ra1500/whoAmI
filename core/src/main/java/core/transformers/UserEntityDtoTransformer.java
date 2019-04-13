package core.transformers;

import db.entity.UserEntity;
import model.UserEntityDto;
import org.springframework.stereotype.Component;

@Component
public class UserEntityDtoTransformer {

    public UserEntityDtoTransformer() {
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

        return dto;
    }

    public UserEntity generate(final UserEntityDto dto) {
        return new UserEntity(dto.getUserName(),dto.getPassword());
    }
}
