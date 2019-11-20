package db.repository;

import db.entity.UserAnswersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface UserAnswersRepositoryDAO extends JpaRepository<UserAnswersEntity, Long> {

    UserAnswersEntity findOneById(Long id); // used in UserAnswersEntity Test
    UserAnswersEntity findOneByUserNameAndAuditeeAndQuestionsEntityId(String userName, String auditee, Long questionsEntityId);
    UserAnswersEntity findOneByUserNameAndAuditeeAndQuestionSetVersionEntityId(String userName, String auditee, Long questionSetVersionEntityId);

    @Query("SELECT a FROM UserAnswersEntity a JOIN FETCH a.questionsEntity b WHERE a.userName = :userName AND  auditee = :auditee AND b.sequenceNumber = 1")
    UserAnswersEntity findOneByUserNameAndAuditee(@Param("userName") String userName, @Param("auditee") String auditee);

    Set<UserAnswersEntity> findAllByUserNameAndAuditeeAndQuestionSetVersionEntityId(String userName, String auditee, Long questinSetVersionEntityId); // used in setting auditors

    @Query("SELECT SUM(answerPoints) FROM UserAnswersEntity WHERE userName = :userName AND questionSetVersionEntityId = :questionSetVersionEntityId AND auditee = :auditee")
    Long findUserScoresTotal(@Param("userName") String userName, @Param("auditee") String auditee, @Param("questionSetVersionEntityId") Long questionSetVersionEntityId);

    @Query(value = "SELECT a FROM UserAnswersEntity a WHERE (auditee = :user AND userName = :friend) OR  (auditee = :user AND userName = :user)")
    List<UserAnswersEntity> findAuditDetails(String friend, String user);

    @Transactional
    Integer deleteAllByUserNameAndAuditeeAndQuestionSetVersionEntityId(String userName, String auditee, Long questionSetVersionEntityId);


}