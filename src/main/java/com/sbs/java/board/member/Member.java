package com.sbs.java.board.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Member {
  private int id;
  private LocalDateTime regDate;
  private LocalDateTime updateDate;
  private String loginId;
  private String loginPw;
  private String name;

  public Member(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (LocalDateTime) articleMap.get("regDate");
    this.updateDate = (LocalDateTime) articleMap.get("updateDate");
    this.loginId = (String) articleMap.get("loginId");
    this.loginPw = (String) articleMap.get("loginPw");
    this.name = (String) articleMap.get("name");
  }

}
