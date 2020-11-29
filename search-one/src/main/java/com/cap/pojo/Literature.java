package com.cap.pojo;

public class Literature {
    private String bookName;//文档名
    private String author;//出处
    private String url;//url地址

    public Literature() {
    }

    public Literature(String bookName, String author, String url) {
        this.bookName = bookName;
        this.author = author;
        this.url = url;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Literature{" +
                "bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
