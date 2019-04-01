package main;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
public class InquiryEntity implements Serializable {

    @Id  //JPA indicating primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gid;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date created;

    @Column
    private Long categoryId;

    @Column
    private String description;

    @Column
    private String rawCsvData;

    public InquiryEntity() {
        super();
    }

    public InquiryEntity(Long categoryId, String description, String rawCsvData) {
        super();
        this.categoryId = categoryId;
        this.description = description;
        this.rawCsvData = rawCsvData;
    }

    public InquiryEntity(Long gid) {
        super();
        this.gid = gid;
    }

    public InquiryEntity(String description) {
        super();
        this.description = description;
    }

    // 'Long' constructor above in gid
    /*public DataEntity(Long categoryId) {
        super();
        this.categoryId = categoryId;
    } */

    public Long getGid() { return gid; }

    public Date getCreated() { return created; }

    public Long getCategoryId() { return categoryId; }

    public String getDescription() { return description; }

    public String getRawCsvData() { return rawCsvData; }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("submission profile", gid, created, categoryId, description);
    }

}
