package db.repository;

import db.entity.UserAnswersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserAnswersRepositoryDAO extends JpaRepository<UserAnswersEntity, Long> {

    UserAnswersEntity findOneByUserIdAndQuestionId(Long userId, Long questionId);

    @Query("SELECT SUM(answerPoints) FROM UserAnswersEntity WHERE userId = :userId")
    Long findUserScoresTotal(@Param("userId") Long userId);

    @Transactional
    Integer deleteOneByUserIdAndQuestionId(Long userId, Long questionId);
}