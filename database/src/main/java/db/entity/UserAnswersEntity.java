package db.entity;

import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
public class UserAnswersEntity implements Serializable {

    @Id  //JPA indicating primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gid;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date created;

    @Column
    private String userName;

    @Column
    private Long questionId;

    @Column
    private String answer;

    @Column(length = 4)
    private Long answerPoints;

    @Column
    private Long questionSetVersion;

    @Column
    private String auditee;

    @Column
    private String comments;

    public UserAnswersEntity() {
        super();
    }

    public UserAnswersEntity(String userName, Long questionId, String answer, Long answerPoints, Long questionSetVersion,
            String auditee, String comments) {
        super();
        this.userName = userName;
        this.questionId = questionId;
        this.answer = answer;
        this.answerPoints = answerPoints;
        this.questionSetVersion = questionSetVersion;
        this.auditee = auditee;
        this.comments = comments;
    }

    public UserAnswersEntity(Long gid) {
        super();
        this.gid = gid;
    }

    public UserAnswersEntity(String userName, Long questionId) {
        super();
        this.userName = userName;
        this.questionId = questionId;
    }

    public UserAnswersEntity(String userName, String auditee, Long questionSetVersion, Long questionId) {
        super();
        this.userName = userName;
        this.auditee = auditee;
        this.userName = userName;
        this.questionSetVersion = questionSetVersion;
    }

    public UserAnswersEntity(String userName, Long questionId, Long questionSetVersion, Long answerPoints, String answer) {
        super();
        this.userName = userName;
        this.questionId = questionId;
        this.questionSetVersion = questionSetVersion;
        this.answerPoints = answerPoints;
        this.answer = answer;
    }

    public Long getGid() { return gid; }

    public Date getCreated() { return created; }

    public String getUserName() { return userName; }

    public Long getQuestionId() { return questionId; }

    public String getAnswer() { return answer; }

    public Long getAnswerPoints() { return answerPoints; }

    public Long getQuestionSetVersion() { return questionSetVersion; }

    public void setUserName(String userName) {
        this.userName = userName;
    } // used for Crud update test

    public String getAuditee() {
        return auditee;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setAnswerPoints(Long answerPoints) {
        this.answerPoints = answerPoints;
    }

    public void setQuestionSetVersion(Long questionSetVersion) {
        this.questionSetVersion = questionSetVersion;
    }

    public void setAuditee(String auditee) {
        this.auditee = auditee;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return String.format("UserAnswers profile", gid, created, userName, questionId, answer, answerPoints, questionSetVersion);
    }

}
