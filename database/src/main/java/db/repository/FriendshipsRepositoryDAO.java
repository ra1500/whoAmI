package db.repository;

import db.entity.FriendshipsEntity;
import db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
public interface FriendshipsRepositoryDAO extends JpaRepository<FriendshipsEntity, Long> {

    FriendshipsEntity findOneById(Long id);
    FriendshipsEntity findOneByUserEntityAndFriend(UserEntity userEntity, String friend);
    Set<FriendshipsEntity> findAllByUserEntity(UserEntity userEntity);
    FriendshipsEntity findOneByUserEntityIdAndFriend(Long userEntityId, String friend);

    @Transactional
    Integer deleteOneById(Long id);
}
