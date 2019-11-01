package db.repository;

import db.entity.PermissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PermissionsRepositoryDAO extends JpaRepository<PermissionsEntity, Long> {

    PermissionsEntity findOneByUserNameAndAuditeeAndQuestionSetVersion(String userName, String auditee, Long questionSetVersion);
    PermissionsEntity findOneByUserNameAndAuditee(String userName, String auditee);
    List<PermissionsEntity> findAllByAuditee(String auditee);
}
