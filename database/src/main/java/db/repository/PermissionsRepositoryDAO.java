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

    @Transactional
    Integer deleteOneById(Long id);

    @Query("SELECT p FROM PermissionsEntity p WHERE p.userName = :userName AND p.typeNumber = 9 OR p.typeNumber = 10")
    Set<PermissionsEntity> getPrivateProfilePageQsets(String userName);

//    TypeIndex	    score	viewer	            owner	    view group	        PermissionType
//    typenumber	score	username	        auditee	    group	            type/
//      1		            everyone	        company	    company	            View QuestionSet
//      2		            everyone w/login	company	    company	            View QuestionSet
//      3		            self	            self	    self	            View QuestionSet
//      4		            (List of friends)	self	    Network ALL	        View QuestionSet
//      5		            (List of friends)	self	    Network Friend	    View QuestionSet
//      6		            (List of friends)	self	    Network Colleague	View QuestionSet
//      7		            (List of friends)	self	    Network Other	    View QuestionSet
//      8		            (individual)	    self	    Individual	        View QuestionSet
//      9	        score	self	            self	    public page	        Score
//      10	        score	self	            self	    private profile pageScore
//      11	        score	(List of friends)	self	    Network ALL	        Score
//      12	        score	(List of friends)	self	    Network Friend	    Score
//      13	        score	(List of friends)	self	    Network Colleague	Score
//      14	        score	(List of friends)	self	    Network Other	    Score
//      15	        score	(individual)	    self	    (individual)	    Score

}
