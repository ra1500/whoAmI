package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

//import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAnswersEntityDto implements Serializable {

    @JsonProperty("gid")
    private Long gid;

    @JsonProperty("created")
    private Date created;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("questionId")
    private Long questionId;

    @JsonProperty("answer")
    private String answer;

    @JsonProperty("answerPoints")
    private Long answerPoints;

    @JsonProperty("questionSetVersion")
    private Long questionSetVersion;

    @JsonProperty("auditee")
    private String auditee;

    @JsonProperty("comments")
    private String comments;

    public UserAnswersEntityDto() {
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(final Long gid) {
        this.gid = gid;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public String getUserName() { return userName; }

    public void setUserName(final String userName) { this.userName = userName; }

    public Long getQuestionId() { return questionId; }

    public void setQuestionId(final Long questionId) { this.questionId = questionId; }

    public String getAnswer() { return answer; }

    public void setAnswer(final String answer) { this.answer = answer; }

    public Long getAnswerPoints() { return answerPoints; }

    public void setAnswerPoints(final Long answerPoints) { this.answerPoints = answerPoints; }

    public Long getQuestionSetVersion() { return questionSetVersion; }

    public void setQuestionSetVersion(final Long questionSetVersion) { this.questionSetVersion = questionSetVersion; }

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
}
