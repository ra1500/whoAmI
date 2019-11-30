package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import db.entity.QuestionsEntity;

//import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionSetVersionEntityDto implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("created")
    private Date created;

    @JsonProperty("title")
    private String title;

    @JsonProperty("category")
    private String category;

    @JsonProperty("description")
    private String description;

    @JsonProperty("version")
    private String version;

    @JsonProperty("creativeSource")
    private String creativeSource;

    @JsonProperty("scoringStyle")
    private Long scoringStyle;

    @JsonProperty("badges")
    private Long badges;

    @JsonProperty("result1")
    private String result1;

    @JsonProperty("result2")
    private String result2;

    @JsonProperty("result3")
    private String result3;

    @JsonProperty("result4")
    private String result4;

    @JsonProperty("result1start")
    private Long result1start;

    @JsonProperty("result2start")
    private Long result2start;

    @JsonProperty("result3start")
    private Long result3start;

    @JsonProperty("result4start")
    private Long result4start;

    public QuestionSetVersionEntityDto() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCreativeSource() {
        return creativeSource;
    }

    public void setCreativeSource(String creativeSource) {
        this.creativeSource = creativeSource;
    }

    public Long getScoringStyle() {
        return scoringStyle;
    }

    public void setScoringStyle(Long scoringStyle) {
        this.scoringStyle = scoringStyle;
    }

    public Long getBadges() {
        return badges;
    }

    public void setBadges(Long badges) {
        this.badges = badges;
    }

    public String getResult1() {
        return result1;
    }

    public void setResult1(String result1) {
        this.result1 = result1;
    }

    public String getResult2() {
        return result2;
    }

    public void setResult2(String result2) {
        this.result2 = result2;
    }

    public String getResult3() {
        return result3;
    }

    public void setResult3(String result3) {
        this.result3 = result3;
    }

    public String getResult4() {
        return result4;
    }

    public void setResult4(String result4) {
        this.result4 = result4;
    }

    public Long getResult1start() {
        return result1start;
    }

    public void setResult1start(Long result1start) {
        this.result1start = result1start;
    }

    public Long getResult2start() {
        return result2start;
    }

    public void setResult2start(Long result2start) {
        this.result2start = result2start;
    }

    public Long getResult3start() {
        return result3start;
    }

    public void setResult3start(Long result3start) {
        this.result3start = result3start;
    }

    public Long getResult4start() {
        return result4start;
    }

    public void setResult4start(Long result4start) {
        this.result4start = result4start;
    }

}
