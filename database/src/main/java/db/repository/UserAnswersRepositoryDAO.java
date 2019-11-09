package db.repository;

import db.entity.UserAnswersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.Set;

@Repository
public interface UserAnswersRepositoryDAO extends JpaRepository<UserAnswersEntity, Long> {

    UserAnswersEntity findOneById(Long id); // used in UserAnswersEntity Test
    UserAnswersEntity findOneByUserNameAndAuditeeAndQuestionsEntityId(String userName, String auditee, Long questionsEntityId);

    @Query("SELECT SUM(answerPoints) FROM UserAnswersEntity WHERE userName = :userName AND questionSetVersionEntityId = :questionSetVersionEntityId AND auditee = :auditee")
    Long findUserScoresTotal(@Param("userName") String userName, @Param("auditee") String auditee, @Param("questionSetVersionEntityId") Long questionSetVersionEntityId);

    @Transactional
    Integer deleteAllByUserNameAndAuditeeAndQuestionSetVersionEntityId(String userName, String auditee, Long questionSetVersionEntityId);

    // WORKS. testing on Profile page....
    //@Query("Select a FROM UserAnswersEntity a")
    //Set<UserAnswersEntity> findSome();

    // SELECT b, p FROM Book b, Publisher p
    @Query("Select a, b FROM UserAnswersEntity a, PermissionsEntity b")
    Set<UserAnswersEntity> findSome();

}