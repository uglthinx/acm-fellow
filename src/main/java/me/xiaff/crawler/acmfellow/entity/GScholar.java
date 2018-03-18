package me.xiaff.crawler.acmfellow.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "google_scholar")
public class GScholar {
    @Id
    private String id;

    private String name;

    private String fullName;

    private String url;

    private Integer citeNum;

    private Integer hIndex;

    @Column(name = "i10_index")
    private Integer i10Index;

    private String avatarLink;

    private String affiliation;

    private String homeLink;

    private String fields;


    @Temporal(value = TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateTime;

    public GScholar() {
    }

    public GScholar(String id, String name, String fullName, String url) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.url = url;
    }

    public GScholar(String id, String name, String fullName, String url, Integer citeNum, Integer hIndex, Integer i10Index, String avatarLink, String affiliation, String homeLink, String fields, Date createTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.url = url;
        this.citeNum = citeNum;
        this.hIndex = hIndex;
        this.i10Index = i10Index;
        this.avatarLink = avatarLink;
        this.affiliation = affiliation;
        this.homeLink = homeLink;
        this.fields = fields;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCiteNum() {
        return citeNum;
    }

    public void setCiteNum(Integer citeNum) {
        this.citeNum = citeNum;
    }

    public Integer gethIndex() {
        return hIndex;
    }

    public void sethIndex(Integer hIndex) {
        this.hIndex = hIndex;
    }

    public Integer getI10Index() {
        return i10Index;
    }

    public void setI10Index(Integer i10Index) {
        this.i10Index = i10Index;
    }

    public String getAvatarLink() {
        return avatarLink;
    }

    public void setAvatarLink(String avatarLink) {
        this.avatarLink = avatarLink;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getHomeLink() {
        return homeLink;
    }

    public void setHomeLink(String homeLink) {
        this.homeLink = homeLink;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
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
        return "GScholar{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", url='" + url + '\'' +
                ", citeNum=" + citeNum +
                ", hIndex=" + hIndex +
                ", i10Index=" + i10Index +
                ", avatarLink='" + avatarLink + '\'' +
                ", affiliation='" + affiliation + '\'' +
                ", homeLink='" + homeLink + '\'' +
                ", fields='" + fields + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
