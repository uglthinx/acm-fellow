package me.xiaff.crawler.acmfellow.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "full_fellow_feature")
public class FullFellowFeature {

    @Id
    private Long id;

    private String name;

    private String type1;

    private String type2;

    private String dblpLink;

    private int waitYear;

    private int pubNum;

    @Temporal(value = TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateTime;

    public FullFellowFeature() {
    }

    public FullFellowFeature(Long id, String name, String type1, String type2, String dblpLink, int waitYear, int pubNum, Date createTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.type1 = type1;
        this.type2 = type2;
        this.dblpLink = dblpLink;
        this.waitYear = waitYear;
        this.pubNum = pubNum;
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

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getDblpLink() {
        return dblpLink;
    }

    public void setDblpLink(String dblpLink) {
        this.dblpLink = dblpLink;
    }

    public int getWaitYear() {
        return waitYear;
    }

    public void setWaitYear(int waitYear) {
        this.waitYear = waitYear;
    }

    public int getPubNum() {
        return pubNum;
    }

    public void setPubNum(int pubNum) {
        this.pubNum = pubNum;
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
        return "FullFellowFeature{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type1='" + type1 + '\'' +
                ", type2='" + type2 + '\'' +
                ", dblpLink='" + dblpLink + '\'' +
                ", waitYear=" + waitYear +
                ", pubNum=" + pubNum +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
