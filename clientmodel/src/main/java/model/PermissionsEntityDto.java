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

    @JsonProperty("viewGroup")
    private String viewGroup;

    @JsonProperty("type")
    private String type;

    @JsonProperty("typeNumber")
    private Long typeNumber;

    @JsonProperty("score")
    private Long score;

    @JsonProperty("result")
    private String result;

    @JsonProperty("badge")
    private Long badge;

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
}
