package db.repository;

import db.entity.QuestionSetVersionEntity;
import db.entity.QuestionsEntity;
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

    @Query("SELECT a FROM UserAnswersEntity a JOIN FETCH a.questionsEntity b WHERE a.userName = :userName AND auditee = :auditee AND b.sequenceNumber = 1")
    UserAnswersEntity findOneByUserNameAndAuditee(@Param("userName") String userName, @Param("auditee") String auditee);

    @Query("SELECT a FROM UserAnswersEntity a JOIN FETCH a.questionsEntity b WHERE a.userName = :userName AND auditee = :auditee AND b.sequenceNumber = 1")
    Set<UserAnswersEntity> findAllByUserNameAndAuditee(@Param("userName") String userName, @Param("auditee") String auditee);

    @Query("SELECT a FROM UserAnswersEntity a JOIN FETCH a.questionsEntity b WHERE a.userName = :userName AND b.sequenceNumber = 1")
    Set<UserAnswersEntity> findAllByUserNameAlerts(@Param("userName") String userName);

    Set<UserAnswersEntity> findAllByUserNameAndAuditeeAndQuestionSetVersionEntityId(String userName, String auditee, Long questinSetVersionEntityId); // used in setting auditors

    @Query("SELECT SUM(answerPoints) FROM UserAnswersEntity WHERE userName = :userName AND questionSetVersionEntityId = :questionSetVersionEntityId AND auditee = :auditee")
    Long findUserScoresTotal(@Param("userName") String userName, @Param("auditee") String auditee, @Param("questionSetVersionEntityId") Long questionSetVersionEntityId);

    @Query("SELECT COUNT(distinct a.questionSetVersionEntity) FROM UserAnswersEntity a WHERE a.userName <> :userName AND auditee = :userName")
    Long getCountAuditInvites(String userName);

    @Query("SELECT a FROM UserAnswersEntity a JOIN FETCH a.questionSetVersionEntity b WHERE a.userName <> :userName AND auditee = :userName AND b.id = :questionSetVersionEntityId")
    Set<UserAnswersEntity> findAllByUserNameAndAuditeeDifferent(String userName, Long questionSetVersionEntityId);

    // TODO provide the original user's answers also so that comparison is easy between original answer and the auditors answer
    @Query("SELECT a FROM UserAnswersEntity a JOIN a.questionSetVersionEntity b WHERE a.userName = :friend AND auditee = :user AND b.id = :questionSetVersionEntityId")
    List<UserAnswersEntity> findAuditDetails(String friend, String user, Long questionSetVersionEntityId);

    @Transactional
    Integer deleteAllByUserNameAndAuditeeAndQuestionSetVersionEntityId(String userName, String auditee, Long questionSetVersionEntityId);

    @Transactional
    Integer deleteAllByQuestionsEntity(QuestionsEntity questionsEntity);

    @Transactional
    Integer deleteAllByAuditeeAndQuestionSetVersionEntity(String auditee, QuestionSetVersionEntity questionSetVersionEntity);

}