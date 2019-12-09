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

    PermissionsEntity findOneByUserNameAndAuditeeAndQuestionSetVersionEntityId(String userName, String auditee, Long questionSetVersionEntityId);
    PermissionsEntity findOneByUserNameAndTypeNumberAndQuestionSetVersionEntityId(String userName, Long typeNumber, Long questionSetVersionEntityId);
    PermissionsEntity findOneByIdAndUserName(Long Id, String userName);
    PermissionsEntity findOneById(Long Id);


    @Query("SELECT p FROM PermissionsEntity p JOIN p.questionSetVersionEntity b WHERE b.id = :questionSetVersionEntityId AND p.userName = :userName AND p.typeNumber BETWEEN '9' AND 15")
    PermissionsEntity findOneByUserNameAndQuestionSetVersionEntityId(String userName, Long questionSetVersionEntityId);

    @Transactional // PermissionsController
    Integer deleteOneById(Long id);

    @Transactional // used in QuestionSetVersionController when a user deletes their Qset.
    Integer deleteAllByQuestionSetVersionEntityId(Long id);

    @Transactional // used to delete a '16' permission when an auditor deletes their answers.
    Integer deleteOneByUserNameAndAuditeeAndQuestionSetVersionEntity(String user, String auditee, QuestionSetVersionEntity foundQuestionSetVersionEntity);

    @Query("SELECT p FROM PermissionsEntity p WHERE p.userName = :userName AND p.typeNumber BETWEEN '9' AND 15")
    Set<PermissionsEntity> getPrivateProfilePageQsets(String userName);

    @Query("SELECT p FROM PermissionsEntity p WHERE p.userName = :userName AND p.typeNumber = 9")
    Set<PermissionsEntity> getPublicProfilePageQsets(String userName);

    @Query("SELECT p FROM PermissionsEntity p WHERE p.typeNumber = 1 OR p.typeNumber = 2")
    Set<PermissionsEntity> getScoresPageQsets();

    @Query("SELECT p FROM PermissionsEntity p WHERE p.userName = :userName AND p.auditee = :friend AND p.typeNumber BETWEEN '11' AND '15' OR p.auditee = :friend AND p.typeNumber = '9'")
    Set<PermissionsEntity> getNetworkContactQsets(String userName, String friend);

    @Query("SELECT p FROM PermissionsEntity p WHERE p.userName = :userName AND p.auditee = :userName AND p.typeNumber = 3")
    Set<PermissionsEntity> getPrivateProfilePageSelfMadeQsets(String userName);

    @Query("SELECT p FROM PermissionsEntity p WHERE p.userName = :userName AND p.auditee != :userName AND p.typeNumber BETWEEN '4' AND '8'")
    Set<PermissionsEntity> getNetworkCreatedQsets(String userName);

    @Query("SELECT p FROM PermissionsEntity p JOIN FETCH p.questionSetVersionEntity b WHERE b.id = :questionSetVersionEntityId AND p.auditee = :userName AND p.typeNumber = 16")
    Set<PermissionsEntity> getAudits(String userName, Long questionSetVersionEntityId);

    @Query("SELECT p FROM PermissionsEntity p JOIN FETCH p.questionSetVersionEntity b WHERE p.auditee = :userName AND p.typeNumber = 16")
    Set<PermissionsEntity> getAuditsRecent(String userName);


//    TypeIndex	    score	viewer	            owner	    view group	        PermissionType
//    typenumber	score	username	        auditee	    group	            type/
//
//      1		            everyone	        company	    company	            View QuestionSet
//      2		            everyone w/login	company	    company	            View QuestionSet
//      3		            self	            self	    self	            View QuestionSet
//      4		            (List of friends)	self	    Network ALL	        View QuestionSet
//      5		            (List of friends)	self	    Network Friend	    View QuestionSet
//      6		            (List of friends)	self	    Network Colleague	View QuestionSet
//      7		            (List of friends)	self	    Network Other	    View QuestionSet
//      8		            (individual)	    self	    Individual	        View QuestionSet
//
//      9	        score	self	            self	    public page	        Score
//      10	        score	self	            self	    private profile pageScore
//      11	        score	(List of friends)	self	    Network ALL	        Score
//      12	        score	(List of friends)	self	    Network Friend	    Score
//      13	        score	(List of friends)	self	    Network Colleague	Score
//      14	        score	(List of friends)	self	    Network Other	    Score
//      15	        score	(individual)	    self	    (individual)	    Score immutable! don't change typeNumbe. Can only delete.

//      16          score                                                       audit score

}
