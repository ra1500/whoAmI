package db.repository;

import db.entity.QuestionSetVersionEntity;
import db.entity.QuestionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface QuestionsRepositoryDAO extends JpaRepository<QuestionsEntity, Long> {

    QuestionsEntity findOneByGid(Long gid);
    QuestionsEntity findOneByQuestionSetVersionEntityAndSequenceNumber(QuestionSetVersionEntity questionSetVersionEntity, Long sequenceNumber);
    Set<QuestionsEntity> findAllByQuestionSetVersionEntity(QuestionSetVersionEntity questionSetVersionEntity);

    // delete. not used....
    //@Query("SELECT MAX(sequenceNumber) FROM QuestionsEntity")
    //Long findMaxQtyQuestions2();

    // indexed parameter (first method parameter is indicated as ?1)
    @Query("SELECT MAX(sequenceNumber) FROM QuestionsEntity WHERE questionSetVersion = ?1")
    Long findMaxQtyQuestions(Long questionSetVersion);

    @Query("SELECT SUM(maxPoints) FROM QuestionsEntity WHERE questionSetVersion = ?1")
    Long PointsForQuestionSetVersion(Long questionSetVersion);

    @Query("Select a FROM QuestionsEntity a")
    Set<QuestionsEntity> findSome();


}

