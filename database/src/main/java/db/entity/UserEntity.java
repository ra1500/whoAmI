package db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@DynamicUpdate
@Table(uniqueConstraints= @UniqueConstraint(columnNames={"userName"}))
@Entity
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private Set<FriendshipsEntity> friendsSet = new HashSet<>();

    public UserEntity() {
        super();
    }

    public UserEntity (String userName, String password, String publicProfile) {
        super();
        this.userName = userName;
        this.password = password;
        this.publicProfile = publicProfile;
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

    public Long getId() { return id; }

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

    public Set<FriendshipsEntity> getFriendsSet() { return friendsSet; }

    public void setFriendsSet(Set<FriendshipsEntity> friendsSet) {
        this.friendsSet = friendsSet;
    }

    @Override
    public String toString() {
        return String.format("User Profile", id, created, userName, password, publicProfile);
    }

}