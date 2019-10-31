package db.repository;

import db.entity.UserAnswersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;

@Repository
public interface UserAnswersRepositoryDAO extends JpaRepository<UserAnswersEntity, Long> {

    UserAnswersEntity findOneByUserNameAndQuestionSetVersionAndQuestionId(String userName, Long questionSetVersion, Long questionId);

    @Query("SELECT SUM(answerPoints) FROM UserAnswersEntity WHERE userName = :userName AND questionSetVersion = :questionSetVersion")
    Long findUserScoresTotal(@Param("userName") String userName, @Param("questionSetVersion") Long questionSetVersion);

    @Transactional
    Integer deleteOneByUserNameAndQuestionSetVersionAndQuestionId(String userName, Long questionSetVersion,Long questionId);

    @Transactional
    Integer deleteAllByUserNameAndQuestionSetVersion(String userName, Long questionSetVersion);

}