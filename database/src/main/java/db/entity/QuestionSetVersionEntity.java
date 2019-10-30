package db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @JsonIgnore
    @OneToMany(mappedBy = "questionSetVersionEntity",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<QuestionsEntity> questionsList = new ArrayList<>();

    @Column (length = 20)
    private String version;

    @Column (length = 20)
    private String title;

    @Column (length = 200)
    private String description;

    @Column (length = 40)
    private String category;

    public QuestionSetVersionEntity() {
        super();
    }

    public QuestionSetVersionEntity (Long gid) {
        super();
        this.gid = gid;
    }

    public Long getGid() { return gid; }

    public Date getCreated() { return created; }

    public List<QuestionsEntity> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List<QuestionsEntity> questionsList) {
        this.questionsList = questionsList;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format("QuestionSetVersion Profile", gid, created, title, version, category, description, questionsList);
    }

}
