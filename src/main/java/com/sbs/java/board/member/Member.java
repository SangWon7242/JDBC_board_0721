package com.sbs.java.board.member;

import java.time.LocalDateTime;
import java.util.Map;

public class Member {
  private final int id;
  private final LocalDateTime regDate;
  private final LocalDateTime updateDate;
  private final String loginId;
  private String loginPw;
  private final String name;

  public Member(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (LocalDateTime) articleMap.get("regDate");
    this.updateDate = (LocalDateTime) articleMap.get("updateDate");
    this.loginId = (String) articleMap.get("loginId");
    this.loginPw = (String) articleMap.get("loginPw");
    this.name = (String) articleMap.get("name");
  }


  @Override
  public String toString() {
    return "Member{" +
        "id=" + id +
        ", regDate=" + regDate +
        ", updateDate=" + updateDate +
        ", loginId='" + loginId + '\'' +
        ", loginPw='" + loginPw + '\'' +
        ", name='" + name + '\'' +
        '}';
  }
}
