package com.cap.pojo;
import java.util.List;
public class Info {
private String id;//id
private String cnName;//中文名
private String enName;//外文名
private String abbreviation;//简称
private String content;//内容
private List<String> tags;//标签
private List<Literature> literatureList;//文献

    @Override
    public String toString() {
        return "Info{" +
                "id='" + id + '\'' +
                ", cnName='" + cnName + '\'' +
                ", enName='" + enName + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", content='" + content + '\'' +
                ", tags=" + tags +
                ", literatureList=" + literatureList +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Literature> getLiteratureList() {
        return literatureList;
    }

    public void setLiteratureList(List<Literature> literatureList) {
        this.literatureList = literatureList;
    }

    public Info() {
    }

    public Info(String id, String cnName, String enName, String abbreviation, String content, List<String> tags, List<Literature> literatureList) {
        this.id = id;
        this.cnName = cnName;
        this.enName = enName;
        this.abbreviation = abbreviation;
        this.content = content;
        this.tags = tags;
        this.literatureList = literatureList;
    }
}
