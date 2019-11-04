package db.repository;

import db.entity.PermissionsEntity;
import db.entity.QuestionSetVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface QuestionSetVersionRepositoryDAO extends JpaRepository<QuestionSetVersionEntity, Long> {

    //QuestionSetVersionEntity findOneByGid(Long gid);
    QuestionSetVersionEntity findOneByQuestionSetVersion(Long questionSetVersion);

    //Set<QuestionSetVersionEntity> findAllByPermissionsEntity(PermissionsEntity permissionsEntity);

}