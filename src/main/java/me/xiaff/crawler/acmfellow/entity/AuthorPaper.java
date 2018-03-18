package me.xiaff.crawler.acmfellow.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.autoconfigure.web.ResourceProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Dell-lcw on 2017/4/7.
 */
@Entity
@Table(name = "author_paper")
public class AuthorPaper {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String bibId;
    private Integer year;
    private Integer citationCount;

//    @Temporal(value = TemporalType.TIMESTAMP)
//    @CreationTimestamp
//    private Date createTime;
//
//    @Temporal(value = TemporalType.TIMESTAMP)
//    @UpdateTimestamp
//    private Date updateTime;


    public AuthorPaper() {
    }

    public AuthorPaper(String authorName, String bibId, Integer year) {
        this.author = authorName;
        this.bibId = bibId;
        this.year = year;
    }

    public AuthorPaper(Long id, String authorName, String bibId, Integer year, Integer citation, Date createTime, Date updateTime) {
        this.id = id;
        this.author = authorName;
        this.bibId = bibId;
        this.year = year;
        this.citationCount = citation;
//        this.createTime = createTime;
//        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getCitationCount() {
        return citationCount;
    }

    public void setCitationCount(Integer citationCount) {
        this.citationCount = citationCount;
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


//    public Date getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(Date createTime) {
//        this.createTime = createTime;
//    }
//
//    public Date getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime(Date updateTime) {
//        this.updateTime = updateTime;
//    }

    @Override
    public String toString() {
        return "AuthorPaper{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", bibId='" + bibId + '\'' +
                ", year=" + year +
                ", citationCount=" + citationCount +
//                ", createTime=" + createTime +
//                ", updateTime=" + updateTime +
                '}';
    }
}
