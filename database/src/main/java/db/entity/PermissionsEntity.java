package db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class PermissionsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date created;

    @Column(name = "userName", length = 100)
    private String userName; // auditor/answeror

    @Column (length = 100)
    private String auditee; // auditee

    @Column (length = 20)
    private String viewGroup;

    @Column  (length = 50) // Type: Qset view permission or userScore permission
    private String type;

    @Column (length = 4)
    private Long typeNumber;

    @Column (length = 4)
    private Long score;

    @Column (length = 40) // what the score means.
    private String result;

    @Column  (length = 3) // badge type to display.
    private Long badge;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "questionSetVersionEntityId")
    private QuestionSetVersionEntity questionSetVersionEntity;

    public PermissionsEntity() {
        super();
    }

    public PermissionsEntity(String userName, String auditee, String viewGroup, String type, Long typeNumber, Long score, String result, Long badge ,QuestionSetVersionEntity questionSetVersionEntity) {
        this.userName = userName;
        this.auditee = auditee;
        this.viewGroup = viewGroup;
        this.type = type;
        this.typeNumber = typeNumber;
        this.score = score;
        this.result = result;
        this.badge = badge;
        this.questionSetVersionEntity = questionSetVersionEntity;
    }

    // constructor used in QuestionSetVersionEntityService.
    public PermissionsEntity(String userName, String auditee, String viewGroup, String type, Long typeNumber, Long score,
                             String result, Long badge) {
        this.userName = userName;
        this.auditee = auditee;
        this.viewGroup = viewGroup;
        this.type = type;
        this.typeNumber = typeNumber;
        this.result = result;
        this.badge = badge;
        this.score = score;
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

    public String getAuditee() {
        return auditee;
    }

    public void setAuditee(String auditee) {
        this.auditee = auditee;
    }

    public String getViewGroup() {
        return viewGroup;
    }

    public void setViewGroup(String viewGroup) {
        this.viewGroup = viewGroup;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getTypeNumber() {
        return typeNumber;
    }

    public void setTypeNumber(Long typeNumber) {
        this.typeNumber = typeNumber;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getBadge() {
        return badge;
    }

    public void setBadge(Long badge) {
        this.badge = badge;
    }

    public QuestionSetVersionEntity getQuestionSetVersionEntity() {
        return questionSetVersionEntity;
    }

    public void setQuestionSetVersionEntity(QuestionSetVersionEntity questionSetVersionEntity) {
        this.questionSetVersionEntity = questionSetVersionEntity;
    }

    @Override
    public String toString() {
        return String.format("Permissions profile", id, created, userName, auditee, viewGroup, type, result, badge);
    }

}