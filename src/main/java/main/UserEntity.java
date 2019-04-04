package main;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

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

    @Column (unique = true, nullable = false)
    private String userName;

    @Column (nullable = false)
    private String password;

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

    public String getPassword() { return password; }

    @Override
    public String toString() {
        return String.format("User Profile", gid, created, userName, password);
    }

}