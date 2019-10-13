package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

//import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionSetVersionEntityDto implements Serializable {

    @JsonProperty("gid")
    private Long gid;

    @JsonProperty("created")
    private Date created;

    @JsonProperty("questionIdList")
    private String questionIdList;

    public QuestionSetVersionEntityDto() {
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

    public String getQuestionIdList() { return questionIdList; }

    public void setQuestionIdList(final String questionIdList) { this.questionIdList = questionIdList; }

}
