package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

//import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionsEntityDto implements Serializable {

    @JsonProperty("gid")
    private Long gid;

    @JsonProperty("created")
    private Date created;

    @JsonProperty("question")
    private String question;

    @JsonProperty("category")
    private String category;

    @JsonProperty("maxPoints")
    private Long maxPoints;

    @JsonProperty("answer1")
    private String answer1;

    @JsonProperty("answer1Points")
    private Long answer1Points;

    @JsonProperty("answer2")
    private String answer2;

    @JsonProperty("answer2Points")
    private Long answer2Points;

    @JsonProperty("answer3")
    private String answer3;

    @JsonProperty("answer3Points")
    private Long answer3Points;

    @JsonProperty("answer4")
    private String answer4;

    @JsonProperty("answer4Points")
    private Long answer4Points;

    @JsonProperty("answer5")
    private String answer5;

    @JsonProperty("answer5Points")
    private Long answer5Points;

    @JsonProperty("answer6")
    private String answer6;

    @JsonProperty("answer6Points")
    private Long answer6Points;

    public QuestionsEntityDto() {
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

    public String getQuestion() { return question; }

    public void setQuestion(final String question) { this.question = question; }

    public String getCategory() { return category; }

    public void setCategory(final String category) { this.category = category; }

    public Long getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(final Long maxPoints) {
        this.maxPoints = maxPoints;
    }

    public String getAnswer1() { return answer1; }

    public void setAnswer1(final String answer1) { this.answer1 = answer1; }

    public Long getAnswer1Points() {
        return answer1Points;
    }

    public void setAnswer1Points(final Long answer1Points) {
        this.answer1Points = answer1Points;
    }

    public String getAnswer2() { return answer2; }

    public void setAnswer2(final String answer2) { this.answer2 = answer2; }

    public Long getAnswer2Points() { return answer2Points; }

    public void setAnswer2Points(final Long answer2Points) { this.answer2Points = answer2Points; }

    public String getAnswer3() { return answer3; }

    public void setAnswer3(final String answer3) { this.answer3 = answer3; }

    public Long getAnswer3Points() {
        return answer3Points;
    }

    public void setAnswer3Points(final Long answer3Points) {
        this.answer3Points = answer3Points;
    }

    public String getAnswer4() { return answer4; }

    public void setAnswer4(final String answer4) { this.answer4 = answer4; }

    public Long getAnswer4Points() {
        return answer4Points;
    }

    public void setAnswer4Points(final Long answer4Points) {
        this.answer4Points = answer4Points;
    }

    public String getAnswer5() { return answer5; }

    public void setAnswer5(final String answer5) { this.answer5 = answer5; }

    public Long getAnswer5Points() {
        return answer5Points;
    }

    public void setAnswer5Points(final Long answer5Points) {
        this.answer5Points = answer5Points;
    }

    public String getAnswer6() { return answer6; }

    public void setAnswer6(final String answer6) { this.answer6 = answer6; }

    public Long getAnswer6Points() {
        return answer6Points;
    }

    public void setAnswer6Points(final Long answer6Points) {
        this.answer6Points = answer6Points;
    }
}

