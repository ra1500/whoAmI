package db.repository;

import db.entity.PermissionsEntity;
import db.entity.QuestionSetVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface QuestionSetVersionRepositoryDAO extends JpaRepository<QuestionSetVersionEntity, Long> {

    //QuestionSetVersionEntity findOneById(Long id);
    QuestionSetVersionEntity findOneById(Long questionSetVersionEntityId);
    //QuestionSetVersionEntity findOneByQuestionSetId(Long questionSetId);

    @Query("SELECT COUNT(a) FROM QuestionSetVersionEntity a WHERE creativeSource = :userName")
    Long countQuantityQsetsByUsername(String userName);

    //@Query("SELECT SUM(answerPoints) FROM UserAnswersEntity WHERE userName = :userName AND questionSetVersion = :questionSetVersion AND auditee = :auditee")
    //QuestionSetVersionEntity findQsetWithChildren();

    // works. gets permissions and Qsets.
    //@Query("SELECT a, b FROM QuestionSetVersionEntity a JOIN a.questionsSet b")
    //Set<QuestionSetVersionEntity> findAllByPermission();

}