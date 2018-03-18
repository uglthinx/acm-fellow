package me.xiaff.crawler.acmfellow.entity;

import me.xiaff.crawler.acmfellow.util.EnglishNameUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;

@Entity
@Table(name = "aminer_scholar")
public class AminerScholar {
    @Id
    @GeneratedValue
    private Long id;

    private String aminerId;

    private String name;

    private String fullName;

    private String gender;

    private Integer hIndex;

    private Integer paperNum;

    private Integer citationNum;

    private String position;

    private String affiliation;

    private String fields;

    private String page;

    private String phdSchool;

    private String phdSchoolLink;

    private Boolean acmFellow;

    private Boolean ieeeFellow;

    private String dblpLink;

    private Boolean crawled;

    @Temporal(value = TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createTime;

    @Temporal(value = TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updateTime;

    public AminerScholar() {
    }

    public AminerScholar(String aminerId, String name, String fullName, String gender, Integer hIndex, Integer paperNum, Integer citationNum, String position, String affiliation, String fields, String page, String phdSchool, String phdSchoolLink, Boolean acmFellow, Boolean ieeeFellow, String dblpLink, Boolean crawled, Date createTime, Date updateTime) {
        this.aminerId = aminerId;
        this.name = name;
        this.fullName = fullName;
        this.gender = gender;
        this.hIndex = hIndex;
        this.paperNum = paperNum;
        this.citationNum = citationNum;
        this.position = position;
        this.affiliation = affiliation;
        this.fields = fields;
        this.page = page;
        this.phdSchool = phdSchool;
        this.phdSchoolLink = phdSchoolLink;
        this.acmFellow = acmFellow;
        this.ieeeFellow = ieeeFellow;
        this.dblpLink = dblpLink;
        this.crawled = crawled;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAminerId() {
        return aminerId;
    }

    public void setAminerId(String aminerId) {
        this.aminerId = aminerId;
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
        String[] tokens = fullName.split(" ");
        String[] nameTokens = Arrays.copyOfRange(tokens, 0, tokens.length - 1);
        this.name = EnglishNameUtils.toCommaSepName(fullName);
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer gethIndex() {
        return hIndex;
    }

    public void sethIndex(Integer hIndex) {
        this.hIndex = hIndex;
    }

    public Integer getPaperNum() {
        return paperNum;
    }

    public void setPaperNum(Integer paperNum) {
        this.paperNum = paperNum;
    }

    public Integer getCitationNum() {
        return citationNum;
    }

    public void setCitationNum(Integer citationNum) {
        this.citationNum = citationNum;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPhdSchool() {
        return phdSchool;
    }

    public void setPhdSchool(String phdSchool) {
        this.phdSchool = phdSchool;
    }

    public String getPhdSchoolLink() {
        return phdSchoolLink;
    }

    public void setPhdSchoolLink(String phdSchoolLink) {
        this.phdSchoolLink = phdSchoolLink;
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

    public Boolean getAcmFellow() {
        return acmFellow;
    }

    public void setAcmFellow(Boolean acmFellow) {
        this.acmFellow = acmFellow;
    }

    public Boolean getIeeeFellow() {
        return ieeeFellow;
    }

    public void setIeeeFellow(Boolean ieeeFellow) {
        this.ieeeFellow = ieeeFellow;
    }

    public String getDblpLink() {
        return dblpLink;
    }

    public void setDblpLink(String dblpLink) {
        this.dblpLink = dblpLink;
    }

    @Override
    public String toString() {
        return "AminerScholar{" +
                "id=" + id +
                ", aminerId='" + aminerId + '\'' +
                ", name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                ", gender='" + gender + '\'' +
                ", hIndex=" + hIndex +
                ", paperNum=" + paperNum +
                ", citationNum=" + citationNum +
                ", position='" + position + '\'' +
                ", affiliation='" + affiliation + '\'' +
                ", fields='" + fields + '\'' +
                ", page='" + page + '\'' +
                ", phdSchool='" + phdSchool + '\'' +
                ", phdSchoolLink='" + phdSchoolLink + '\'' +
                ", acmFellow=" + acmFellow +
                ", ieeeFellow=" + ieeeFellow +
                ", dblpLink='" + dblpLink + '\'' +
                ", crawled=" + crawled +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
