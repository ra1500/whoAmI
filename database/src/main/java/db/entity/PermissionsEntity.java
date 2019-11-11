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

    @Column(name = "userName")
    private String userName; // auditor/answeror

    @Column
    private String auditee; // auditee

    @Column
    private String viewGroup; // QsetPerm:   AnswerPerm: viewGroup can view

    @Column  // Type: Qset view permission or userScore permission
    private String type;

    @Column
    private Long typeNumber;

    @Column
    private Long score;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "questionSetVersionEntityId")
    private QuestionSetVersionEntity questionSetVersionEntity;

    public PermissionsEntity() {
        super();
    }

    public PermissionsEntity(String userName, String auditee, String viewGroup, String type, Long typeNumber, Long score, QuestionSetVersionEntity questionSetVersionEntity) {
        this.userName = userName;
        this.auditee = auditee;
        this.viewGroup = viewGroup;
        this.type = type;
        this.typeNumber = typeNumber;
        this.score = score;
        this.questionSetVersionEntity = questionSetVersionEntity;
    }

    // constructor used in QuestionSetVersionEntityService.
    public PermissionsEntity(String userName, String auditee, String viewGroup, String type, Long typeNumber, Long score) {
        this.userName = userName;
        this.auditee = auditee;
        this.viewGroup = viewGroup;
        this.type = type;
        this.typeNumber = typeNumber;
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

    public QuestionSetVersionEntity getQuestionSetVersionEntity() {
        return questionSetVersionEntity;
    }

    public void setQuestionSetVersionEntity(QuestionSetVersionEntity questionSetVersionEntity) {
        this.questionSetVersionEntity = questionSetVersionEntity;
    }

    @Override
    public String toString() {
        return String.format("Permissions profile", id, created, userName, auditee, viewGroup, type);
    }

}