package db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

@Entity
@Table
public class QuestionSetVersionEntity implements Serializable{

    @Id  //JPA indicating primary key
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gid;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date created;

    @ManyToMany(mappedBy = "questionSetVersionEntities", fetch = FetchType.EAGER)
    private Set<PermissionsEntity> permissionsEntities = new HashSet<>();

    @Column(name = "questionSetVersion")
    private Long questionSetVersion;

    @JsonIgnore
    @OneToMany(mappedBy = "questionSetVersionEntity",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<QuestionsEntity> questionsSet = new HashSet<>();

    @Column (length = 20)
    private String version;

    @Column (length = 20)
    private String title;

    @Column (length = 200)
    private String description;

    @Column (length = 40)
    private String category;

    @Column (length = 40)
    private String creativeSource;

    public QuestionSetVersionEntity() {
        super();
    }

    public QuestionSetVersionEntity (Long questionSetVersion) {
        super();
        this.questionSetVersion = questionSetVersion;
    }

    public QuestionSetVersionEntity(String title, Long questionSetVersion) {
        this.questionSetVersion = questionSetVersion;
        this.title = title;
    }

    public Set<PermissionsEntity> getPermissionsEntities() {
        return permissionsEntities;
    }

    public void setPermissionsEntities(Set<PermissionsEntity> permissionsEntities) {
        this.permissionsEntities = permissionsEntities;
    }

    public Long getGid() { return gid; }

    public Date getCreated() { return created; }

    public Long getQuestionSetVersion() {
        return questionSetVersion;
    }

    public void setQuestionSetVersion(Long questionSetVersion) {
        this.questionSetVersion = questionSetVersion;
    }

    public Set<QuestionsEntity> getQuestionsSet() {
        return questionsSet;
    }

    public void setQuestionsSet(Set<QuestionsEntity> questionsSet) {
        this.questionsSet = questionsSet;
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

    public String getCreativeSource() {
        return creativeSource;
    }

    public void setCreativeSource(String creativeSource) {
        this.creativeSource = creativeSource;
    }

    @Override
    public String toString() {
        return String.format("QuestionSetVersion Profile", gid, created, questionSetVersion, version, category, description, questionsSet, creativeSource);
    }

}
