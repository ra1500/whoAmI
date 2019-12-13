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

    @Column (length = 60)
    private String description;

    @Column (length = 20)
    private String version;

    @Column (length = 100)
    private String creativeSource;

    @Column (length = 3)
    private Long scoringStyle; // 1 for display at completed qset. 2 for displayed/continuous scoring,

    @Column (length = 3)
    private Long displayAnswers; // 1 for ability to show full list of correct answers after completion. 2 to hide.

    @Column (length = 3)
    private Long badges; // which types of ribbons/badges used to match with results (1 'company created qset' badges or 2 'user created qset' badges.

    @Column (length = 40)
    private String result1;

    @Column (length = 40)
    private String result2;

    @Column (length = 40)
    private String result3;

    @Column (length = 40)
    private String result4;

    @Column (length = 3)
    private Long result1start;

    @Column (length = 3)
    private Long result2start;

    @Column (length = 3)
    private Long result3start;

    public QuestionSetVersionEntity() {
        super();
    }

    public QuestionSetVersionEntity(String title, String category, String description, String version, String creativeSource,
                                    Long scoringStyle, Long displayAnswers ,Long badges, String result1, String result2, String result3, String result4,
                                    Long result1start, Long result2start,
                                    Long result3start) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.version = version;
        this.creativeSource = creativeSource;
        this.scoringStyle = scoringStyle;
        this.displayAnswers = displayAnswers;
        this.badges = badges;
        this.result1 = result1;
        this.result2 = result2;
        this.result3 = result3;
        this.result4 = result4;
        this.result1start = result1start;
        this.result2start = result2start;
        this.result3start = result3start;
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

    public Long getScoringStyle() {
        return scoringStyle;
    }

    public void setScoringStyle(Long scoringStyle) {
        this.scoringStyle = scoringStyle;
    }

    public Long getDisplayAnswers() {
        return displayAnswers;
    }

    public void setDisplayAnswers(Long displayAnswers) {
        this.displayAnswers = displayAnswers;
    }

    public Long getBadges() {
        return badges;
    }

    public void setBadges(Long badges) {
        this.badges = badges;
    }

    public String getResult1() {
        return result1;
    }

    public void setResult1(String result1) {
        this.result1 = result1;
    }

    public String getResult2() {
        return result2;
    }

    public void setResult2(String result2) {
        this.result2 = result2;
    }

    public String getResult3() {
        return result3;
    }

    public void setResult3(String result3) {
        this.result3 = result3;
    }

    public String getResult4() {
        return result4;
    }

    public void setResult4(String result4) {
        this.result4 = result4;
    }

    public Long getResult1start() {
        return result1start;
    }

    public void setResult1start(Long result1start) {
        this.result1start = result1start;
    }

    public Long getResult2start() {
        return result2start;
    }

    public void setResult2start(Long result2start) {
        this.result2start = result2start;
    }

    public Long getResult3start() {
        return result3start;
    }

    public void setResult3start(Long result3start) {
        this.result3start = result3start;
    }


    @Override
    public String toString() {
        return String.format("QuestionSetVersion Profile", id, created, title, category, description, version, creativeSource,
                scoringStyle, displayAnswers,badges, result1, result2, result3, result4, result1start, result2start,
                result3start);
    }

}
