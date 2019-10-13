package db.repository;

import db.entity.UserAnswersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnswersRepositoryDAO extends JpaRepository<UserAnswersEntity, Long> {

    UserAnswersEntity findOneByUserIdAndQuestionId(Long userId, Long questionId);

}