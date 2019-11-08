package db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import java.io.Serializable;
import javax.persistence.*;
import java.util.*;

@Entity
@Table
public class QuestionSetVersionEntity implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date created;

    @Column (length = 20)
    private String title;

    @Column (length = 40)
    private String category;

    @Column (length = 200)
    private String description;

    @Column (length = 20)
    private String version;

    @Column (length = 40)
    private String creativeSource;

    public QuestionSetVersionEntity() {
        super();
    }

    public QuestionSetVersionEntity(String title, String category, String description, String version, String creativeSource) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.version = version;
        this.creativeSource = creativeSource;
    }

    // constructor used in UserAnswersEntity Test.
    public QuestionSetVersionEntity(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCreativeSource() {
        return creativeSource;
    }

    public void setCreativeSource(String creativeSource) {
        this.creativeSource = creativeSource;
    }

    @Override
    public String toString() {
        return String.format("QuestionSetVersion Profile", id, created, title, category, description, version, creativeSource);
    }

}
