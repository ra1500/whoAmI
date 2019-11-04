package db.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
public class QuestionsEntity implements Serializable {

    @Id  //JPA indicating primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long gid;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date created;

    @ManyToOne
    @JoinColumn(name = "questionSetVersion")
    private QuestionSetVersionEntity questionSetVersionEntity;

    @Column
    private String creativeSource;

    @Column
    private Long sequenceNumber;

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

    public QuestionsEntity() {
        super();
    }

    public QuestionsEntity(Long gid) {
        super();
        this.gid = gid;
    }

    public QuestionsEntity(String question) {
        super();
        this.question = question;
    }

    public QuestionsEntity(QuestionSetVersionEntity questionSetVersionEntity, String question, String category,
                           Long maxPoints, String answer1, Long answer1Points,
                           String answer2, Long answer2Points, String answer3, Long answer3Points, String answer4,
                           Long answer4Points, String answer5, Long answer5Points, String answer6, Long answer6Points)
    {
        super();
        this.questionSetVersionEntity = questionSetVersionEntity;
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


    public Long getGid() { return gid; }

    public Date getCreated() { return created; }

    public Long getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Long sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public QuestionSetVersionEntity getQuestionSetVersionEntity() {
        return questionSetVersionEntity;
    }

    public String getQuestion() { return question; }

    public String getCategory() { return category; }

    public Long getMaxPoints() { return maxPoints; }

    public String getAnswer1() { return answer1; }

    public Long getAnswer1Points() { return answer1Points; }

    public String getAnswer2() { return answer2; }

    public Long getAnswer2Points() { return answer2Points; }

    public String getAnswer3() { return answer3; }

    public Long getAnswer3Points() { return answer3Points; }

    public String getAnswer4() { return answer4; }

    public Long getAnswer4Points() { return answer4Points; }

    public String getAnswer5() { return answer5; }

    public Long getAnswer5Points() { return answer5Points; }

    public String getAnswer6() { return answer6; }

    public Long getAnswer6Points() { return answer6Points; }

    public void setCategory(String category) {
        this.category = category;
    } // used for Crud update test

    @Override
    public String toString() {
        return String.format("question profile", gid, created, question, category);
    }

}

