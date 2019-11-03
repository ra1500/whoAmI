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

    @JsonProperty("gid")
    private Long gid;

    //@JsonProperty("created")
    //private Date created;

    @JsonProperty("setNumber")
    private Long setNumber;

    //@JsonProperty("questionsList")
    //private Set<QuestionsEntity> questionsSet;

    @JsonProperty("version")
    private String version;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("category")
    private String category;

    @JsonProperty("creativeSource")
    private String creativeSource;

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


    public Long getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(Long setNumber) {
        this.setNumber = setNumber;
    }

    //public Set<QuestionsEntity> getQuestionsSet() {
    //    return questionsSet;
    //}

    //public void setQuestionsSet(Set<QuestionsEntity> questionsSet) {
    //    this.questionsSet = questionsSet;
    //}

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

    public String getCreativeSource() {
        return creativeSource;
    }

    public void setCreativeSource(String creativeSource) {
        this.creativeSource = creativeSource;
    }
}
