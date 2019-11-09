package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import db.entity.FriendshipsEntity;

//import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEntityDto implements Serializable {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("created")
    private Date created;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("publicProfile")
    private String publicProfile;

    @JsonProperty("friendsList")
    private Set<FriendshipsEntity> friendsSet;

    public UserEntityDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPublicProfile() {
        return publicProfile;
    }

    public void setPublicProfile(String publicProfile) {
        this.publicProfile = publicProfile;
    }

    public Set<FriendshipsEntity> getFriendsSet() {
        return friendsSet;
    }

    public void setFriendsSet(Set<FriendshipsEntity> friendsSet) {
        this.friendsSet = friendsSet;
    }
}
