package me.xiaff.crawler.acmfellow.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "msa_author_paper")
public class MsaAuthorPaper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long authorId;

    private String authorName;

    private long paperId;

    private int year;

    private int citation;

    @Temporal(value = TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateTime;

    public MsaAuthorPaper(long authorId, String authorName, long paperId, int year, int citation) {
        this.authorId = authorId;
        this.authorName = authorName;
        this.paperId = paperId;
        this.year = year;
        this.citation = citation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public long getPaperId() {
        return paperId;
    }

    public void setPaperId(long paperId) {
        this.paperId = paperId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCitation() {
        return citation;
    }

    public void setCitation(int citation) {
        this.citation = citation;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "MsaAuthorPaper{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                ", paperId=" + paperId +
                ", year=" + year +
                ", citation=" + citation +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
