package db.repository;

import db.entity.QuestionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepositoryDAO extends JpaRepository<QuestionsEntity, Long> {

    QuestionsEntity findOneByGid(Long gid);

    //@Query("SELECT max(QuestionsEntity.gid) from QuestionsEntity")
}

