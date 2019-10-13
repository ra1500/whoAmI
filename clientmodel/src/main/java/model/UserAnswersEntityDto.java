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

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("questionId")
    private Long questionId;

    @JsonProperty("answer")
    private String answer;

    @JsonProperty("answerPoints")
    private Long answerPoints;

    @JsonProperty("questionSetVersion")
    private Long questionSetVersion;

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

    public Long getUserId() { return userId; }

    public void setUserId(final Long userId) { this.userId = userId; }

    public Long getQuestionId() { return questionId; }

    public void setQuestionId(final Long questionId) { this.questionId = questionId; }

    public String getAnswer() { return answer; }

    public void setAnswer(final String answer) { this.answer = answer; }

    public Long getAnswerPoints() { return answerPoints; }

    public void setAnswerPoints(final Long answerPoints) { this.answerPoints = answerPoints; }

    public Long getQuestionSetVersion() { return questionSetVersion; }

    public void setQuestionSetVersion(final Long questionSetVersion) { this.questionSetVersion = questionSetVersion; }
}
