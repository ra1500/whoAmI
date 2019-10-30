package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import db.entity.QuestionsEntity;

//import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionSetVersionEntityDto implements Serializable {

    @JsonProperty("gid")
    private Long gid;

    //@JsonProperty("created")
    //private Date created;

    @JsonProperty("questionsList")
    private List<QuestionsEntity> questionsList;

    @JsonProperty("version")
    private String version;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category")
    private String category;

    public QuestionSetVersionEntityDto() {
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(final Long gid) {
        this.gid = gid;
    }

    //public Date getCreated() {
    //    return created;
    //}

    //public void setCreated(final Date created) {
    //    this.created = created;
    //}

    public List<QuestionsEntity> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<QuestionsEntity> questionsList) {
        this.questionsList = questionsList;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
