package model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import db.entity.UserEntity;

//import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FriendshipsEntityDto implements Serializable {

    @JsonProperty("gid")
    private Long gid;

    //@JsonProperty("created")
    //private Date created;

    // Here, return the full UserEntity object since this is for GET
    @JsonProperty("userEntity")
    private UserEntity userEntity;

    @JsonProperty("inviter")
    private String inviter;

    @JsonProperty("friend")
    private String friend;

    @JsonProperty("status")
    private String status;

    @JsonProperty("connectionType")
    private String connectionType;

    @JsonProperty("visibilityPermission")
    private String visibilityPermission;

    public FriendshipsEntityDto() {
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

    public UserEntity getUserEntity() { return userEntity; }

    public void setUserEntity(UserEntity userEntity) { this.userEntity = userEntity; }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public String getFriend() {
        return friend;
    }

    public void setFriend(String invitationRecipient) {
        this.friend = friend;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public String getVisibilityPermission() {
        return visibilityPermission;
    }

    public void setVisibilityPermission(String visibilityPermission) {
        this.visibilityPermission = visibilityPermission;
    }
}
