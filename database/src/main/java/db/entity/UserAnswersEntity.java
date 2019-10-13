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
    private Long userId;

    @Column
    private Long questionId;

    @Column
    private String answer;

    @Column(length = 3)
    private Long answerPoints;

    @Column
    private Long questionSetVersion;

    public UserAnswersEntity() {
        super();
    }

    public UserAnswersEntity(Long userId, Long questionId, String answer, Long answerPoints, Long questionSetVersion) {
        super();
        this.userId = userId;
        this.questionId = questionId;
        this.answer = answer;
        this.answerPoints = answerPoints;
        this.questionSetVersion = questionSetVersion;
    }

    public UserAnswersEntity(Long gid) {
        super();
        this.gid = gid;
    }

    public UserAnswersEntity(Long userId, Long questionId) {
        super();
        this.userId = userId;
        this.questionId = questionId;
    }

    public UserAnswersEntity(Long userId, Long questionId, Long questionSetVersion, Long answerPoints, String answer) {
        super();
        this.userId = userId;
        this.questionId = questionId;
        this.questionSetVersion = questionSetVersion;
        this.answerPoints = answerPoints;
        this.answer = answer;
    }

    public Long getGid() { return gid; }

    public Date getCreated() { return created; }

    public Long getUserId() { return userId; }

    public Long getQuestionId() { return questionId; }

    public String getAnswer() { return answer; }

    public Long getAnswerPoints() { return answerPoints; }

    public Long getQuestionSetVersion() { return questionSetVersion; }

    public void setUserId(Long userId) {
        this.userId = userId;
    } // used for Crud update test

    @Override
    public String toString() {
        return String.format("UserAnswers profile", gid, created, userId, questionId, answer, answerPoints, questionSetVersion);
    }

}
