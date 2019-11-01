package db.entity;

import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
public class PermissionsEntity implements Serializable {

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
    private String auditee;

    @Column
    private String networkProfilePagePermission;

    @Column
    private Long questionSetVersion;

    @Column  // not used currently. available for future use.
    private String tbd;

    public PermissionsEntity() {
        super();
    }

    public PermissionsEntity(String userName, String auditee, String networkProfilePagePermission, Long questionSetVersion, String tbd) {
        super();
        this.userName = userName;
        this.auditee = auditee;
        this.networkProfilePagePermission = networkProfilePagePermission;
        this.questionSetVersion = questionSetVersion;
        this.tbd = tbd;
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

    public String getNetworkProfilePagePermission() {
        return networkProfilePagePermission;
    }

    public void setNetworkProfilePagePermission(String networkProfilePagePermission) {
        this.networkProfilePagePermission = networkProfilePagePermission;
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
        return String.format("Permissions profile", gid, created, userName, auditee, networkProfilePagePermission,
                questionSetVersion, tbd);
    }

}