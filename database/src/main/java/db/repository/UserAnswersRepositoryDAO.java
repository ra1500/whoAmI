package db.repository;

import db.entity.UserAnswersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface UserAnswersRepositoryDAO extends JpaRepository<UserAnswersEntity, Long> {

    UserAnswersEntity findOneById(Long id); // used in UserAnswersEntity Test
    UserAnswersEntity findOneByUserNameAndAuditeeAndQuestionsEntityId(String userName, String auditee, Long questionsEntityId);


    Set<UserAnswersEntity> findAllByUserNameAndAuditeeAndQuestionSetVersionEntityId(String userName, String auditee, Long questinSetVersionEntityId); // used in setting auditors

    @Query("SELECT SUM(answerPoints) FROM UserAnswersEntity WHERE userName = :userName AND questionSetVersionEntityId = :questionSetVersionEntityId AND auditee = :auditee")
    Long findUserScoresTotal(@Param("userName") String userName, @Param("auditee") String auditee, @Param("questionSetVersionEntityId") Long questionSetVersionEntityId);

    @Transactional
    Integer deleteAllByUserNameAndAuditeeAndQuestionSetVersionEntityId(String userName, String auditee, Long questionSetVersionEntityId);


}