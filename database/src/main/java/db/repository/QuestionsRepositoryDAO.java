package db.repository;

import db.entity.QuestionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionsRepositoryDAO extends JpaRepository<QuestionsEntity, Long> {

    QuestionsEntity findOneByGid(Long gid);

    @Query("SELECT MAX(gid) FROM QuestionsEntity")
    Long findMaxQtyQuestions();
}

