package me.xiaff.crawler.acmfellow.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "msa_author")
public class MsaAuthor {
    @Id
    private Long id;

    private String name;

    private String fullName;

    private String affiliation;

    private String fields;

    private Boolean consistent;

    private Boolean crawled;

    @Temporal(value = TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateTime;

    public MsaAuthor() {
    }

    public MsaAuthor(Long id, String name, String fullName, String affiliation, String fields) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.affiliation = affiliation;
        this.fields = fields;
    }

    public MsaAuthor(Long id, String name, String fullName, String affiliation, String fields, Boolean consistent, Date createTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.fullName = fullName;
        this.affiliation = affiliation;
        this.fields = fields;
        this.consistent = consistent;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public Boolean getConsistent() {
        return consistent;
    }

    public void setConsistent(Boolean consistent) {
        this.consistent = consistent;
    }

    public Boolean getCrawled() {
        return crawled;
    }

    public void setCrawled(Boolean crawled) {
        this.crawled = crawled;
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
        return "MsaAuthor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", affiliation='" + affiliation + '\'' +
                ", fields='" + fields + '\'' +
                ", consistent=" + consistent +
                ", crawled=" + crawled +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
