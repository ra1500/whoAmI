package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import db.entity.PermissionsEntity;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionsEntityDto implements Serializable {

    //@JsonProperty("gid")
    //private Long gid;

    //@JsonProperty("created")
    //private Date created;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("auditee")
    private String auditee;

    @JsonProperty("networkProfilePagePermission")
    private String networkProfilePagePermission;

    @JsonProperty("questionSetVersion")
    private Long questionSetVersion;

    @JsonProperty("tbd")
    private String tbd;

    public PermissionsEntityDto() {
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
}
