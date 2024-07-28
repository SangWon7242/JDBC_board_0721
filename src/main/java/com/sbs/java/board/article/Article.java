package com.sbs.java.board.article;

import java.time.LocalDateTime;
import java.util.Map;

public class Article {
  private int id;
  private LocalDateTime regDate;
  private LocalDateTime updateDate;
  private String subject;
  private String content;

  public Article(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (LocalDateTime) articleMap.get("regDate");
    this.updateDate = (LocalDateTime) articleMap.get("updateDate");
    this.subject = (String) articleMap.get("subject");
    this.content = (String) articleMap.get("content");
  }

  public Article(int id, LocalDateTime regDate, LocalDateTime updateDate, String subject, String content) {
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
