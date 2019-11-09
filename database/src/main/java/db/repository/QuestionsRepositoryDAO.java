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

    QuestionsEntity findOneById(Long id); // used in UserAnswersEntity Test.
    Set<QuestionsEntity> findAllByQuestionSetVersionEntity(QuestionSetVersionEntity questionSetVersionEntity);
    QuestionsEntity findOneBySequenceNumberAndQuestionSetVersionEntityId(Long sequenceNumber, Long questionSetVersionEntityId);

    // indexed parameter (first method parameter is indicated as ?1)
    @Query("SELECT MAX(sequenceNumber) FROM QuestionsEntity WHERE questionSetVersionEntityId = ?1")
    Long findMaxQtyQuestions(Long questionSetVersion);

    @Query("SELECT SUM(maxPoints) FROM QuestionsEntity WHERE questionSetVersionEntityId = ?1")
    Long PointsForQuestionSetVersion(Long questionSetVersion);

    //@Query("Select a FROM QuestionsEntity a")
    //Set<QuestionsEntity> findSome();


}

