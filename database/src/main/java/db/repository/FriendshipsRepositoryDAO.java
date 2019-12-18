package db.repository;

import db.entity.FriendshipsEntity;
import db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Set;

@Repository
public interface FriendshipsRepositoryDAO extends JpaRepository<FriendshipsEntity, Long> {

    FriendshipsEntity findOneById(Long id);
    //FriendshipsEntity findOneByUserEntityAndFriend(UserEntity userEntity, String friend);
    FriendshipsEntity findOneByUserEntityIdAndId(Long userId, Long friendId);
    FriendshipsEntity findOneByIdAndUserEntityId(Long friendId, Long userId);
    Set<FriendshipsEntity> findAllByUserEntity(UserEntity userEntity);
    FriendshipsEntity findOneByUserEntityIdAndFriend(Long userEntityId, String friend);

    //@Query("SELECT COUNT(a) FROM FriendshipsEntity a JOIN FETCH b.userEntityId WHERE b.id = :userId")
    @Query("SELECT COUNT(a) FROM FriendshipsEntity a WHERE userEntityId = :userId")
    Long countFriends(@Param("userId")Long userId);

    @Transactional
    Integer deleteOneById(Long id);
}
