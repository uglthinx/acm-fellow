package me.xiaff.crawler.acmfellow.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "msa_paper")
public class MsaPaper {
    @Id
    private long id;

    private String title;

    private String authors;

    private String fields;

    private int year;

    private int citation;

    private Integer gCitation;

    private long journalId;
    private String journalName;

    private long conferenceId;
    private String conferenceName;

    @Temporal(value = TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateTime;

    public MsaPaper() {
    }

    public MsaPaper(long id, String title, String authors, String fields, int year, int citation) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.fields = fields;
        this.year = year;
        this.citation = citation;
    }

    public MsaPaper(long id, String title, String authors, String fields, int year, Integer citation, int gCitation, long journalId, String journalName, long conferenceId, String conferenceName, Date createTime, Date updateTime) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.fields = fields;
        this.year = year;
        this.citation = citation;
        this.gCitation = gCitation;
        this.journalId = journalId;
        this.journalName = journalName;
        this.conferenceId = conferenceId;
        this.conferenceName = conferenceName;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
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

    public Integer getgCitation() {
        return gCitation;
    }

    public void setgCitation(Integer gCitation) {
        this.gCitation = gCitation;
    }

    public long getJournalId() {
        return journalId;
    }

    public void setJournalId(long journalId) {
        this.journalId = journalId;
    }

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    public long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getConferenceName() {
        return conferenceName;
    }

    public void setConferenceName(String conferenceName) {
        this.conferenceName = conferenceName;
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
        return "MsaPaper{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors='" + authors + '\'' +
                ", fields='" + fields + '\'' +
                ", year=" + year +
                ", citation=" + citation +
                ", gCitation=" + gCitation +
                ", journalId=" + journalId +
                ", journalName='" + journalName + '\'' +
                ", conferenceId=" + conferenceId +
                ", conferenceName='" + conferenceName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
