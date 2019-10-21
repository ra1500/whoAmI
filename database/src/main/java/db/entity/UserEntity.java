package db.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class UserEntity {

    @Id  //JPA indicating primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gid;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date created;



    @Column (unique = true, nullable = false, length = 20)
    private String userName;

    @Column (nullable = false, length = 20)
    private String password;

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

    public Long getGid() { return gid; }

    public Date getCreated() { return created; }

    public String getUserName() { return userName; }

    public void setUserName(String userName) { this.userName = userName; }

    public String getPassword() { return password; }

    public List<FriendshipsEntity> getFriendsList() { return friendsList; }

    @Override
    public String toString() {
        return String.format("User Profile", gid, created, userName, password);
    }

}