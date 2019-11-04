package db.repository;

import db.entity.PermissionsEntity;
import db.entity.QuestionSetVersionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.Set;

@Repository
public interface PermissionsRepositoryDAO extends JpaRepository<PermissionsEntity, Long> {

    PermissionsEntity findOneByUserNameAndAuditeeAndQuestionSetVersion(String userName, String auditee, Long questionSetVersion);
    PermissionsEntity findOneByUserNameAndAuditee(String userName, String auditee);

    Set<PermissionsEntity> findAllByAuditee(String auditee);
    Set<PermissionsEntity> findByUserName(String userName);

    // works. if data in join table.
    Set<PermissionsEntity> findAllByUserName(String userName);

    //@Query("SELECT questionSetVersion FROM PermissionsEntity WHERE userName = :userName")
    //Set<PermissionsEntity> findAllQsetsPrivateProfile(String userName);

    //@Query("SELECT a FROM PermissionsEntity a")
    //Set<PermissionsEntity> findAllQsetsPrivateProfile(String userName);

    //@Query("SELECT a FROM QuestionSetVersionEntity a")
    //Set<PermissionsEntity> findAllQsetsPrivateProfile(String userName);

    //
    //@Query("SELECT a, b FROM PermissionsEntity a INNER JOIN a.questionSetVersionEntities b")
    //Set<PermissionsEntity> findAllQsetsPrivateProfile(String userName);
}
