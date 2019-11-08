package db.entity;

import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
public class UserAnswersEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date created;

    @Column
    private String userName;

    @Column
    private String answer;

    @Column(length = 4)
    private Long answerPoints;

    @Column
    private String auditee;

    @Column
    private String comments;

    @ManyToOne
    @JoinColumn(name = "questionId")
    private QuestionsEntity questionsEntity;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "questionSetVersionEntityId")
    private QuestionSetVersionEntity questionSetVersionEntity;

    public UserAnswersEntity() {
        super();
    }

    public UserAnswersEntity(String userName, String answer, Long answerPoints, String auditee, String comments,
                             QuestionsEntity questionsEntity, QuestionSetVersionEntity questionSetVersionEntity) {
        this.userName = userName;
        this.answer = answer;
        this.answerPoints = answerPoints;
        this.auditee = auditee;
        this.comments = comments;
        this.questionsEntity = questionsEntity;
        this.questionSetVersionEntity = questionSetVersionEntity;
    }

    // used in UserAnswersEntityService to create a new initial entity before adding parents.
    public UserAnswersEntity(String userName, String answer, Long answerPoints, String auditee, String comments) {
        this.userName = userName;
        this.answer = answer;
        this.answerPoints = answerPoints;
        this.auditee = auditee;
        this.comments = comments;
    }

    // constructor used in UserAnswersEntity Test.
    public UserAnswersEntity(String userName, Long answerPoints) {
        this.userName = userName;
        this.answerPoints = answerPoints;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getAnswerPoints() {
        return answerPoints;
    }

    public void setAnswerPoints(Long answerPoints) {
        this.answerPoints = answerPoints;
    }

    public String getAuditee() {
        return auditee;
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

    public QuestionsEntity getQuestionsEntity() {
        return questionsEntity;
    }

    public void setQuestionsEntity(QuestionsEntity questionsEntity) {
        this.questionsEntity = questionsEntity;
    }

    public QuestionSetVersionEntity getQuestionSetVersionEntity() {
        return questionSetVersionEntity;
    }

    public void setQuestionSetVersionEntity(QuestionSetVersionEntity questionSetVersionEntity) {
        this.questionSetVersionEntity = questionSetVersionEntity;
    }

    @Override
    public String toString() {
        return String.format("UserAnswers profile", id, created, userName, answer, answerPoints, auditee, comments);
    }

}
