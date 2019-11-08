package db.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@DynamicUpdate // needed for patch?
@Table(uniqueConstraints= @UniqueConstraint(columnNames={"userName", "friend"}))
@Entity
public class FriendshipsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date created;

    @ManyToOne
    @JoinColumn(name = "userName") // TODO userName? change to user_id?
    private UserEntity userEntity;

    @Column(length = 20)
    private String inviter; // the user who initiated the invitation to be a friend

    @Column(length = 20) // the user's friend
    private String friend;

    @Column(length = 20)
    private String connectionStatus;

    @Column(length = 20)
    private String connectionType;

    @Column(length = 20)
    private String visibilityPermission;

    public FriendshipsEntity() {
        super();
    }

    public FriendshipsEntity(UserEntity userEntity, String inviter, String friend, String connectionStatus,
                             String connectionType, String visibilityPermission) {
        super();
        this.userEntity = userEntity;
        this.inviter = inviter;
        this.friend = friend;
        this.connectionStatus = connectionStatus;
        this.connectionType = connectionType;
        this.visibilityPermission = visibilityPermission;
    }

    public FriendshipsEntity(UserEntity userEntity, String inviter, String friend, String connectionStatus,
                             Long id) {
        super();
        this.userEntity = userEntity;
        this.inviter = inviter;
        this.friend = friend;
        this.connectionStatus = connectionStatus;
        this.connectionType = connectionType;
        this.visibilityPermission = visibilityPermission;
    }

    public FriendshipsEntity(Long id) {
        super();
        this.id = id;
    }

    public FriendshipsEntity(UserEntity userEntity) {
        super();
        this.userEntity = userEntity;
    }

    public Long getId() { return id; }

    public Date getCreated() { return created; }

    public UserEntity getUserEntity() {return userEntity; }

    public String getInviter() { return inviter; }

    public String getFriend() { return friend; }

    public String getConnectionStatus() { return connectionStatus; }

    public String getConnectionType() { return connectionType; }

    public String getVisibilityPermission() { return visibilityPermission; }

    public void setVisibilityPermission(String visibilityPermission) {
        this.visibilityPermission = visibilityPermission;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    } // used for Crud update test

    @Override
    public String toString() {
        return String.format("friendships profile", id, created, userEntity.getUserName(), inviter, friend, connectionStatus,
                connectionType, visibilityPermission);
    }

}
