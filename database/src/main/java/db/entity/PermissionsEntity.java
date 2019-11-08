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
    private String userName;

    @Column
    private String auditee;

    @Column
    private String profilePageGroup;

    @Column  // not used currently. available for future use.
    private String tbd;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "questionSetVersionEntityId")
    private QuestionSetVersionEntity questionSetVersionEntity;

    public PermissionsEntity() {
        super();
    }

    public PermissionsEntity(String userName, String auditee, String profilePageGroup, String tbd, QuestionSetVersionEntity questionSetVersionEntity) {
        this.userName = userName;
        this.auditee = auditee;
        this.profilePageGroup = profilePageGroup;
        this.tbd = tbd;
        this.questionSetVersionEntity = questionSetVersionEntity;
    }

    // constructor used in QuestionSetVersionEntityService.
    public PermissionsEntity(String userName, String auditee, String profilePageGroup, String tbd) {
        this.userName = userName;
        this.auditee = auditee;
        this.profilePageGroup = profilePageGroup;
        this.tbd = tbd;
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

    public String getProfilePageGroup() {
        return profilePageGroup;
    }

    public void setProfilePageGroup(String profilePageGroup) {
        this.profilePageGroup = profilePageGroup;
    }

    public String getTbd() {
        return tbd;
    }

    public void setTbd(String tbd) {
        this.tbd = tbd;
    }

    public QuestionSetVersionEntity getQuestionSetVersionEntity() {
        return questionSetVersionEntity;
    }

    public void setQuestionSetVersionEntity(QuestionSetVersionEntity questionSetVersionEntity) {
        this.questionSetVersionEntity = questionSetVersionEntity;
    }

    @Override
    public String toString() {
        return String.format("Permissions profile", id, created, userName, auditee, profilePageGroup, tbd);
    }

}