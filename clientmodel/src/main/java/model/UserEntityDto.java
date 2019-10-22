package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import db.entity.FriendshipsEntity;

//import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEntityDto implements Serializable {

    //@JsonProperty("gid")
    //private Long gid;

    //@JsonProperty("created")
    //private Date created;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("friendsList")
    private List<FriendshipsEntity> friendsList;

    public UserEntityDto() {
    }

    //public Long getGid() {
    //    return gid;
    //}

    //public void setGid(final Long gid) {
    //    this.gid = gid;
    //}

    //public Date getCreated() {
    //    return created;
    //}

    //public void setCreated(final Date created) {
    //    this.created = created;
    //}

    public String getUserName() { return userName; }

    public void setUserName(final String userName) { this.userName = userName; }

    public String getPassword() { return password; }

    public void setPassword(final String password) { this.password = password; }

    public List<FriendshipsEntity> getFriendsList() {
        return friendsList;
    }

    public void setFriendsList(List<FriendshipsEntity> friendsList) {
        this.friendsList = friendsList;
    }
}
