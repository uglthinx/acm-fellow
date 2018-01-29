package me.xiaff.crawler.acmfellow.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Dell-lcw on 2017/4/7.
 */
@Entity
@Table(name = "paper_v2")
public class Paper {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String author;
    private String url;
    private String year;
    private String parent;
    private String bibId;
    private String type;
    private Long citation;

    private String citationElement1;
    private String citationElement2;

    private Long citationNum1;
    private Long citationNum2;

    @Temporal(value = TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateTime;

    public Paper() {
    }

    public Paper(String title, String author, String url, String year, String parent, String bibId, String type, Long citation, String citationElement1, String citationElement2, Long citationNum1, Long citationNum2, Date createTime, Date updateTime) {
        this.title = title;
        this.author = author;
        this.url = url;
        this.year = year;
        this.parent = parent;
        this.bibId = bibId;
        this.type = type;
        this.citation = citation;
        this.citationElement1 = citationElement1;
        this.citationElement2 = citationElement2;
        this.citationNum1 = citationNum1;
        this.citationNum2 = citationNum2;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getBibId() {
        return bibId;
    }

    public void setBibId(String bibId) {
        this.bibId = bibId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getCitation() {
        return citation;
    }

    public void setCitation(Long citation) {
        this.citation = citation;
    }

    public String getCitationElement1() {
        return citationElement1;
    }

    public void setCitationElement1(String citationElement1) {
        this.citationElement1 = citationElement1;
    }

    public String getCitationElement2() {
        return citationElement2;
    }

    public void setCitationElement2(String citationElement2) {
        this.citationElement2 = citationElement2;
    }

    public Long getCitationNum1() {
        return citationNum1;
    }

    public void setCitationNum1(Long citationNum1) {
        this.citationNum1 = citationNum1;
    }

    public Long getCitationNum2() {
        return citationNum2;
    }

    public void setCitationNum2(Long citationNum2) {
        this.citationNum2 = citationNum2;
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
        return "Paper{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", url='" + url + '\'' +
                ", year='" + year + '\'' +
                ", parent='" + parent + '\'' +
                ", bibId='" + bibId + '\'' +
                ", type='" + type + '\'' +
                ", citation=" + citation +
                ", citationElement1='" + citationElement1 + '\'' +
                ", citationElement2='" + citationElement2 + '\'' +
                ", citationNum1=" + citationNum1 +
                ", citationNum2=" + citationNum2 +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
