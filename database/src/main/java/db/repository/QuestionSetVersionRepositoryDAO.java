package db.repository;

import db.entity.QuestionSetVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionSetVersionRepositoryDAO extends JpaRepository<QuestionSetVersionEntity, Long> {

    //QuestionSetVersionEntity findOneByGid(Long gid);
    QuestionSetVersionEntity findOneBySetNumber(Long SetNumber);

}