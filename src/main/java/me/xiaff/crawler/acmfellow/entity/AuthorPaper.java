package me.xiaff.crawler.acmfellow.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Dell-lcw on 2017/4/7.
 */
@Entity
@Table(name = "author_paper_v2")
public class AuthorPaper {
    @Id
    @GeneratedValue
    private Long id;
    private String authorName;
    private String bibId;
    private Integer year;
    private Integer citation;

    @Temporal(value = TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateTime;


    public AuthorPaper() {
    }

    public AuthorPaper(String authorName, String bibId, Integer year) {
        this.authorName = authorName;
        this.bibId = bibId;
        this.year = year;
    }

    public AuthorPaper(Long id, String authorName, String bibId, Integer year, Integer citation, Date createTime, Date updateTime) {
        this.id = id;
        this.authorName = authorName;
        this.bibId = bibId;
        this.year = year;
        this.citation = citation;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBibId() {
        return bibId;
    }

    public void setBibId(String bibId) {
        this.bibId = bibId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCitation() {
        return citation;
    }

    public void setCitation(Integer citation) {
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
        return "AuthorPaper{" +
                "id=" + id +
                ", authorName='" + authorName + '\'' +
                ", bibId='" + bibId + '\'' +
                ", year=" + year +
                ", citation=" + citation +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
