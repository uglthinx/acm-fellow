package me.xiaff.crawler.acmfellow.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ieee_fellow_new")
public class FellowDO {
    @Id
    private Long id;

    @Column(name = "fellow_id")
    private String name;

    private String finalPhdSchool;

    public FellowDO() {
    }

    public FellowDO(Long id, String name, String finalPhdSchool) {
        this.id = id;
        this.name = name;
        this.finalPhdSchool = finalPhdSchool;
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

    public String getFinalPhdSchool() {
        return finalPhdSchool;
    }

    public void setFinalPhdSchool(String finalPhdSchool) {
        this.finalPhdSchool = finalPhdSchool;
    }

    @Override
    public String toString() {
        return "FellowDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", finalPhdSchool='" + finalPhdSchool + '\'' +
                '}';
    }
}
