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

    UserAnswersEntity findOneByUserNameAndAuditeeAndQuestionSetVersionAndQuestionId(String userName, String auditee, Long questionSetVersion, Long questionId);

    @Query("SELECT SUM(answerPoints) FROM UserAnswersEntity WHERE userName = :userName AND questionSetVersion = :questionSetVersion AND auditee = :auditee")
    Long findUserScoresTotal(@Param("userName") String userName, @Param("auditee") String auditee, @Param("questionSetVersion") Long questionSetVersion);

    // works
    //@Query("Select a FROM UserAnswersEntity a")
    //Set<UserAnswersEntity> findSome();

    @Transactional
    Integer deleteOneByUserNameAndAuditeeAndQuestionSetVersionAndQuestionId(String userName, String auditee,Long questionSetVersion,Long questionId);

    @Transactional
    Integer deleteAllByUserNameAndAuditeeAndQuestionSetVersion(String userName, String auditee, Long questionSetVersion);

}