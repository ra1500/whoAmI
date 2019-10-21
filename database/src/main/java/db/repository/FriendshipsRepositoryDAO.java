package db.repository;

import db.entity.FriendshipsEntity;
import db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipsRepositoryDAO extends JpaRepository<FriendshipsEntity, Long> {

    FriendshipsEntity findOneByGid(Long gid);
    FriendshipsEntity findOneByUserEntity(UserEntity userEntity);
    List<FriendshipsEntity> findAllByUserEntity(UserEntity userEntity);
}
