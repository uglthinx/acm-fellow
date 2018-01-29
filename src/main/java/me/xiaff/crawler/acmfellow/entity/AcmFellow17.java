package me.xiaff.crawler.acmfellow.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "acm_fellow_17")
public class AcmFellow17 {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String region;

    private Integer selectYear;

    private String type1;

    private String type2;

    private String description;

    private String url;

    @Temporal(value = TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateTime;

    public AcmFellow17() {
    }

    public AcmFellow17(String name, String region, Integer selectYear, String type1, String type2, String description, String url, Date createTime, Date updateTime) {
        this.name = name;
        this.region = region;
        this.selectYear = selectYear;
        this.type1 = type1;
        this.type2 = type2;
        this.description = description;
        this.url = url;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getSelectYear() {
        return selectYear;
    }

    public void setSelectYear(Integer selectYear) {
        this.selectYear = selectYear;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "AcmFellow17{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", selectYear=" + selectYear +
                ", type1='" + type1 + '\'' +
                ", type2='" + type2 + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
