package me.xiaff.crawler.acmfellow.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fellow_list")
public class FellowDO {
    @Id
    private Long id;

    private String name;

    public FellowDO() {
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

    @Override
    public String toString() {
        return "FellowDO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
