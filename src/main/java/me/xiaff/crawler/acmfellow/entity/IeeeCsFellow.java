package me.xiaff.crawler.acmfellow.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ieee_cs_fellow")
public class IeeeCsFellow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer selectYear;

    private String gender;

    private String society;

    private String region;

    private String category;

    private String description;


    @Temporal(value = TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateTime;

    public IeeeCsFellow() {
    }

    public IeeeCsFellow(String name, Integer selectYear, String gender, String society, String region, String category, String description, Date createTime, Date updateTime) {
        this.name = name;
        this.selectYear = selectYear;
        this.gender = gender;
        this.society = society;
        this.region = region;
        this.category = category;
        this.description = description;
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

    public Integer getSelectYear() {
        return selectYear;
    }

    public void setSelectYear(Integer selectYear) {
        this.selectYear = selectYear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSociety() {
        return society;
    }

    public void setSociety(String society) {
        this.society = society;
    }

    public String getCategory() {
        return category;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setCategory(String category) {
        this.category = category;
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

    @Override
    public String toString() {
        return "IeeeCsFellow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", selectYear=" + selectYear +
                ", gender='" + gender + '\'' +
                ", society='" + society + '\'' +
                ", region='" + region + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
