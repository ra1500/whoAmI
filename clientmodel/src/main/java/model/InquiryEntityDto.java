package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

//import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InquiryEntityDto implements Serializable {

    @JsonProperty("gid")
    private Long gid;

    @JsonProperty("created")
    private Date created;

    @JsonProperty("categoryId")
    private Long categoryId;

    @JsonProperty("description")
    private String description;

    @JsonProperty("rawCsvData")
    private String rawCsvData;

    @JsonProperty("username")
    private String username;

    public InquiryEntityDto() {
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(final Long categoryId) {
        this.categoryId = categoryId; }

    public String getDescription() {
        return description; }

    public void setDescription(final String description) {
        this.description = description; }

    public String getRawCsvData() {
        return rawCsvData; }

    public void setRawCsvData(final String rawCsvData) {
        this.rawCsvData = rawCsvData; }

    public String getUsername() {
        return username; }

    public void setUsername(final String username) {
        this.username = username; }

}
