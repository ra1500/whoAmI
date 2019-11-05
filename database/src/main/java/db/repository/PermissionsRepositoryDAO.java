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

    PermissionsEntity findOneByUserNameAndAuditeeAndProfilePageGroupAndQuestionSetVersion(String userName, String auditee, String profilePageGroup, Long questionSetVersion);
    PermissionsEntity findOneByUserNameAndAuditee(String userName, String auditee);
    Set<PermissionsEntity> findAllByAuditee(String auditee);
    Set<PermissionsEntity> findByUserName(String userName);
    Set<PermissionsEntity> findAllByUserName(String userName);


}
