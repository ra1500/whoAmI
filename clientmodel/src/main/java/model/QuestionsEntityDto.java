package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import db.entity.QuestionSetVersionEntity;

//import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionsEntityDto implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("created")
    private Date created;

    @JsonProperty("sequenceNumber")
    private Long sequenceNumber;

    @JsonProperty("creativeSource")
    private String creativeSource;

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

    @JsonProperty("questionSetVersionEntity")
    private QuestionSetVersionEntity questionSetVersionEntity;

    public QuestionsEntityDto() {
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

    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getCreativeSource() {
        return creativeSource;
    }

    public void setCreativeSource(String creativeSource) {
        this.creativeSource = creativeSource;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(Long maxPoints) {
        this.maxPoints = maxPoints;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public Long getAnswer1Points() {
        return answer1Points;
    }

    public void setAnswer1Points(Long answer1Points) {
        this.answer1Points = answer1Points;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public Long getAnswer2Points() {
        return answer2Points;
    }

    public void setAnswer2Points(Long answer2Points) {
        this.answer2Points = answer2Points;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public Long getAnswer3Points() {
        return answer3Points;
    }

    public void setAnswer3Points(Long answer3Points) {
        this.answer3Points = answer3Points;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public Long getAnswer4Points() {
        return answer4Points;
    }

    public void setAnswer4Points(Long answer4Points) {
        this.answer4Points = answer4Points;
    }

    public String getAnswer5() {
        return answer5;
    }

    public void setAnswer5(String answer5) {
        this.answer5 = answer5;
    }

    public Long getAnswer5Points() {
        return answer5Points;
    }

    public void setAnswer5Points(Long answer5Points) {
        this.answer5Points = answer5Points;
    }

    public String getAnswer6() {
        return answer6;
    }

    public void setAnswer6(String answer6) {
        this.answer6 = answer6;
    }

    public Long getAnswer6Points() {
        return answer6Points;
    }

    public void setAnswer6Points(Long answer6Points) {
        this.answer6Points = answer6Points;
    }

    public QuestionSetVersionEntity getQuestionSetVersionEntity() {
        return questionSetVersionEntity;
    }

    public void setQuestionSetVersionEntity(QuestionSetVersionEntity questionSetVersionEntity) {
        this.questionSetVersionEntity = questionSetVersionEntity;
    }
}

