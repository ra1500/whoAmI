package db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DynamicUpdate
@Table(uniqueConstraints= @UniqueConstraint(columnNames={"userName"}))
@Entity
public class UserEntity {

    @Id  //JPA indicating primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gid;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date created;

    @Column (nullable = false, length = 20)
    private String userName;

    @Column (nullable = false, length = 20)
    private String password;

    @Column (length = 20)
    private String publicProfile; // not boolean for possible future variations

    @JsonIgnore
    @OneToMany(mappedBy = "userEntity",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<FriendshipsEntity> friendsList = new ArrayList<>();

    public UserEntity() {
        super();
    }

    public UserEntity (String userName, String password) {
        super();
        this.userName = userName;
        this.password = password;
    }

    public UserEntity (String userName) {
        super();
        this.userName = userName;
    }

    public Long getGid() { return gid; }

    public Date getCreated() { return created; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }

    public String getPublicProfile() {
        return publicProfile;
    }

    public void setPublicProfile(String publicProfile) {
        this.publicProfile = publicProfile;
    }

    public List<FriendshipsEntity> getFriendsList() { return friendsList; }

    public void setFriendsList(List<FriendshipsEntity> friendsList) {
        this.friendsList = friendsList;
    }

    @Override
    public String toString() {
        return String.format("User Profile", gid, created, userName, password);
    }

}