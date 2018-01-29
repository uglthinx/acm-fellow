package me.xiaff.crawler.acmfellow.entity;

public class CiteNumber {
    // Google 学术 被引用次数
    private long scholarCite;

    // Google 搜索第一条结果 被引用次数
    private long searchCite;

    public CiteNumber() {
    }

    public CiteNumber(long scholarCite, long searchCite) {
        this.scholarCite = scholarCite;
        this.searchCite = searchCite;
    }

    public long getScholarCite() {
        return scholarCite;
    }

    public void setScholarCite(long scholarCite) {
        this.scholarCite = scholarCite;
    }

    public long getSearchCite() {
        return searchCite;
    }

    public void setSearchCite(long searchCite) {
        this.searchCite = searchCite;
    }

    @Override
    public String toString() {
        return "CiteNumber{" +
                "scholarCite=" + scholarCite +
                ", searchCite=" + searchCite +
                '}';
    }
}
