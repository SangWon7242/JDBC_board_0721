package com.sbs.java.board.member;

import com.sbs.java.board.util.MysqlUtil;
import com.sbs.java.board.util.SecSql;

import java.util.Map;

public class MemberRepository {
  public Member  findByLoginId(String loginId) {
    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM `member`");
    sql.append("WHERE loginId = ?", loginId);

    Map<String, Object> memberMap = MysqlUtil.selectRow(sql);

    if(memberMap.isEmpty()) {
      return null;
    }

    return new Member(memberMap);
  }

  public void join(String loginId, String loginPw, String name) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO `member`");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", loginId = ?", loginId);
    sql.append(", loginPw = ?", loginPw);
    sql.append(", name = ?", name);

    MysqlUtil.insert(sql);
  }
}
