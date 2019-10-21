package db.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
public class FriendshipsEntity implements Serializable {

    @Id  //JPA indicating primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gid;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date created;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "userName")
    private UserEntity userEntity;

    @Column(length = 20)
    private String inviter; // the user who initiated the invitation to be a friend

    @Column(length = 20) // the user's friend
    private String friend;

    @Column(length = 20)
    private String status; // pending, connected, declined, cancelled

    @Column(length = 20)
    private String connectionType; //friend, colleague, family, online

    @Column(length = 20)
    private String visibilityPermission; // fullyhide, fullyopen, limited

    public FriendshipsEntity() {
        super();
    }

    public FriendshipsEntity(UserEntity userEntity, String inviter, String friend, String status,
                             String connectionType, String visibilityPermission) {
        super();
        this.userEntity = userEntity;
        this.inviter = inviter;
        this.friend = friend;
        this.status = status;
        this.connectionType = connectionType;
        this.visibilityPermission = visibilityPermission;
    }

    public FriendshipsEntity(Long gid) {
        super();
        this.gid = gid;
    }

    public FriendshipsEntity(UserEntity userEntity) {
        super();
        this.userEntity = userEntity;
    }

    public Long getGid() { return gid; }

    public Date getCreated() { return created; }

    public UserEntity getUserEntity() {return userEntity; }

    public String getInviter() { return inviter; }

    public String getFriend() { return friend; }

    public String getStatus() { return status; }

    public String getConnectionType() { return connectionType; }

    public String getVisibilityPermission() { return visibilityPermission; }

    public void setFriend(String friend) {
        this.friend = friend;
    } // used for Crud update test

    @Override
    public String toString() {
        return String.format("friendships profile", gid, created, userEntity.getUserName(), inviter, friend, status,
                connectionType, visibilityPermission);
    }

}
