package db.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class QuestionSetVersionEntity {

    @Id  //JPA indicating primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gid;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date created;

    @Column (nullable = false, length = 20)
    private String questionIdList;

    public QuestionSetVersionEntity() {
        super();
    }

    public QuestionSetVersionEntity (String questionIdList) {
        super();
        this.questionIdList = questionIdList;
    }

    public Long getGid() { return gid; }

    public Date getCreated() { return created; }

    public String getQuestionIdList() { return questionIdList; }

    @Override
    public String toString() {
        return String.format("QuestionSetVersion Profile", gid, created, questionIdList);
    }

}
