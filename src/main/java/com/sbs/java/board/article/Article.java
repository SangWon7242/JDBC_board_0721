package com.sbs.java.board.article;

public class Article {
  private int id;
  private String regDate;
  private String updateDate;
  private String subject;
  private String content;

  public int getId() {
    return id;
  }

  public String getSubject() {
    return subject;
  }

  public String getContent() {
    return content;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Article(int id, String regDate, String updateDate, String subject, String content) {
    this.id = id;
    this.regDate = regDate;
    this.updateDate = updateDate;
    this.subject = subject;
    this.content = content;
  }

  public Article(int id, String subject, String content) {
    this.id = id;
    this.subject = subject;
    this.content = content;
  }

  @Override
  public String toString() {
    return "Article{" +
        "id=" + id +
        ", regDate='" + regDate + '\'' +
        ", updateDate='" + updateDate + '\'' +
        ", subject='" + subject + '\'' +
        ", content='" + content + '\'' +
        '}';
  }
}
