package db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class QuestionsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "questionId")
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date created;

    @Column
    private Long sequenceNumber;

    @Column
    private String creativeSource;

    @Column
    private String question;

    @Column
    private String category;

    @Column(length = 3)
    private Long maxPoints;

    @Column
    private String answer1;

    @Column(length = 3)
    private Long answer1Points;

    @Column
    private String answer2;

    @Column(length = 3)
    private Long answer2Points;

    @Column
    private String answer3;

    @Column(length = 3)
    private Long answer3Points;

    @Column
    private String answer4;

    @Column(length = 3)
    private Long answer4Points;

    @Column
    private String answer5;

    @Column(length = 3)
    private Long answer5Points;

    @Column
    private String answer6;

    @Column(length = 3)
    private Long answer6Points;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "questionSetVersionEntityId")
    private QuestionSetVersionEntity questionSetVersionEntity;

    public QuestionsEntity() {
        super();
    }

    public QuestionsEntity(Long sequenceNumber, String creativeSource, String question, String category, Long maxPoints, String answer1, Long answer1Points, String answer2, Long answer2Points, String answer3, Long answer3Points, String answer4, Long answer4Points, String answer5, Long answer5Points, String answer6, Long answer6Points) {
        this.sequenceNumber = sequenceNumber;
        this.creativeSource = creativeSource;
        this.question = question;
        this.category = category;
        this.maxPoints = maxPoints;
        this.answer1 = answer1;
        this.answer1Points = answer1Points;
        this.answer2 = answer2;
        this.answer2Points = answer2Points;
        this.answer3 = answer3;
        this.answer3Points = answer3Points;
        this.answer4 = answer4;
        this.answer4Points = answer4Points;
        this.answer5 = answer5;
        this.answer5Points = answer5Points;
        this.answer6 = answer6;
        this.answer6Points = answer6Points;
    }

    // constructor used in UserAnswersEntity Test
    public QuestionsEntity(String question) {
        this.question = question;
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

    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getCreativeSource() {
        return creativeSource;
    }

    public void setCreativeSource(String creativeSource) {
        this.creativeSource = creativeSource;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(Long maxPoints) {
        this.maxPoints = maxPoints;
    }

    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public Long getAnswer1Points() {
        return answer1Points;
    }

    public void setAnswer1Points(Long answer1Points) {
        this.answer1Points = answer1Points;
    }

    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    public Long getAnswer2Points() {
        return answer2Points;
    }

    public void setAnswer2Points(Long answer2Points) {
        this.answer2Points = answer2Points;
    }

    public String getAnswer3() {
        return answer3;
    }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public Long getAnswer3Points() {
        return answer3Points;
    }

    public void setAnswer3Points(Long answer3Points) {
        this.answer3Points = answer3Points;
    }

    public String getAnswer4() {
        return answer4;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public Long getAnswer4Points() {
        return answer4Points;
    }

    public void setAnswer4Points(Long answer4Points) {
        this.answer4Points = answer4Points;
    }

    public String getAnswer5() {
        return answer5;
    }

    public void setAnswer5(String answer5) {
        this.answer5 = answer5;
    }

    public Long getAnswer5Points() {
        return answer5Points;
    }

    public void setAnswer5Points(Long answer5Points) {
        this.answer5Points = answer5Points;
    }

    public String getAnswer6() {
        return answer6;
    }

    public void setAnswer6(String answer6) {
        this.answer6 = answer6;
    }

    public Long getAnswer6Points() {
        return answer6Points;
    }

    public void setAnswer6Points(Long answer6Points) {
        this.answer6Points = answer6Points;
    }

    public QuestionSetVersionEntity getQuestionSetVersionEntity() {
        return questionSetVersionEntity;
    }

    public void setQuestionSetVersionEntity(QuestionSetVersionEntity questionSetVersionEntity) {
        this.questionSetVersionEntity = questionSetVersionEntity;
    }

    @Override
    public String toString() {
        return String.format("question profile", id, created, sequenceNumber, creativeSource,question, category, maxPoints, answer1, answer1Points,
                answer2, answer2Points, answer3, answer3Points, answer4, answer4Points, answer5, answer5Points,
                answer6, answer6Points);
    }

}

