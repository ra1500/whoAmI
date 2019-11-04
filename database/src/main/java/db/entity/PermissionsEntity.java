package db.entity;

import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class PermissionsEntity implements Serializable {

    @Id  //JPA indicating primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gid")
    private Long gid;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date created;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "permissions_questionSets",
            joinColumns = { @JoinColumn(name = "pset", referencedColumnName = "questionSetVersion") },
            inverseJoinColumns = { @JoinColumn(name = "qset", referencedColumnName = "gid") }
    )
    Set<QuestionSetVersionEntity> questionSetVersionEntities = new HashSet<>();

    @Column(name = "userName")
    private String userName;

    @Column
    private String auditee;

    @Column
    private String profilePageGroup;

    @Column(name = "questionSetVersion")
    private Long questionSetVersion;

    @Column  // not used currently. available for future use.
    private String tbd;

    public PermissionsEntity() {
        super();
    }

    public PermissionsEntity(String userName) {
        this.userName = userName;
    }

    public PermissionsEntity(Set<QuestionSetVersionEntity> questionSetVersionEntities, String userName, String auditee, String profilePageGroup, Long questionSetVersion, String tbd) {
        this.questionSetVersionEntities = questionSetVersionEntities;
        this.userName = userName;
        this.auditee = auditee;
        this.profilePageGroup = profilePageGroup;
        this.questionSetVersion = questionSetVersion;
        this.tbd = tbd;
    }

    public PermissionsEntity(String auditee, String profilePageGroup, Long questionSetVersion, String tbd) {
        this.auditee = auditee;
        this.profilePageGroup = profilePageGroup;
        this.questionSetVersion = questionSetVersion;
        this.tbd = tbd;
    }

    public PermissionsEntity(String userName, String auditee, Long questionSetVersion) {
        this.userName = userName;
        this.auditee = auditee;
        this.questionSetVersion = questionSetVersion;
    }

    public Set<QuestionSetVersionEntity> getQuestionSetVersionEntities() {
        return questionSetVersionEntities;
    }

    public void setQuestionSetVersionEntities(Set<QuestionSetVersionEntity> questionSetVersionEntities) {
        this.questionSetVersionEntities = questionSetVersionEntities;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
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

    public Long getQuestionSetVersion() {
        return questionSetVersion;
    }

    public void setQuestionSetVersion(Long questionSetVersion) {
        this.questionSetVersion = questionSetVersion;
    }

    public String getTbd() {
        return tbd;
    }

    public void setTbd(String tbd) {
        this.tbd = tbd;
    }

    @Override
    public String toString() {
        return String.format("Permissions profile", gid, created, userName, auditee, profilePageGroup,
                questionSetVersion, tbd);
    }

}