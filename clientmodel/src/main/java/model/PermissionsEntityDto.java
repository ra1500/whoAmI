package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import db.entity.PermissionsEntity;
import db.entity.QuestionSetVersionEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionsEntityDto implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("created")
    private Date created;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("auditee")
    private String auditee;

    @JsonProperty("profilePageGroup")
    private String profilePageGroup;

    @JsonProperty("tbd")
    private String tbd;

    @JsonProperty("questionSetVersionEntity")
    private QuestionSetVersionEntity questionSetVersionEntity;

    public PermissionsEntityDto() {
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
}
