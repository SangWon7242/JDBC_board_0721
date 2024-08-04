package com.sbs.java.board.article;

import com.sbs.java.board.util.MysqlUtil;
import com.sbs.java.board.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleRepository {
  public int write(int memberId, String subject, String content) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", memberId = ?", memberId);
    sql.append(", `subject` = ?", subject);
    sql.append(", content = ?", content);

    return MysqlUtil.insert(sql);
  }

  public List<Article> getArticles(Map<String, Object> args) {

    String searchKeyword = "";
    String searchKeywordTypeCode = "";

    if(args.containsKey("searchKeyword")) {
      searchKeyword = (String) args.get("searchKeyword");
    }

    if(args.containsKey("searchKeywordTypeCode")) {
      searchKeywordTypeCode = (String) args.get("searchKeywordTypeCode");
    }

    int limitFrom = -1;

    if(args.containsKey("limitFrom")) {
      limitFrom = (int) args.get("limitFrom");
    }

    int limitTake = -1;

    if(args.containsKey("limitTake")) {
      limitTake = (int) args.get("limitTake");
    }

    SecSql sql = new SecSql();
    sql.append("SELECT *, M.name AS extra__writerName");
    sql.append("FROM article AS A");
    sql.append("INNER JOIN `member` AS M");
    sql.append("ON A.memberId = M.id");

    if(!searchKeyword.isEmpty()) {
      switch (searchKeywordTypeCode) {
        case "subject" -> sql.append("WHERE A.`subject` LIKE CONCAT('%', ?, '%')", searchKeyword);
        case "content" -> sql.append("WHERE A.content LIKE CONCAT('%', ?, '%')", searchKeyword);
        case "subject,content"
            -> {
          sql.append("WHERE A.`subject` LIKE CONCAT('%', ?, '%')", searchKeyword);
          sql.append("OR A.content LIKE CONCAT('%', ?, '%')", searchKeyword);
        }

      }
    }

    sql.append("ORDER BY A.id DESC");

    if(limitFrom != -1) {
      sql.append("LIMIT ?, ?", limitFrom, limitTake);
    }

    List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(sql);

    List<Article> articles = new ArrayList<>();

    for(Map<String, Object> articleMap : articleListMap) {
      articles.add(new Article(articleMap));
    }

    return articles;
  }

  public Article findByArticleId(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT *, M.name AS extra__writerName");
    sql.append("FROM article AS A");
    sql.append("INNER JOIN `member` AS M");
    sql.append("ON A.memberId = M.id");
    sql.append("WHERE A.id = ?", id);

    Map<String, Object> articleMap = MysqlUtil.selectRow(sql);

    if(articleMap.isEmpty()) {
      return null;
    }

    return new Article(articleMap);
  }

  public void update(int id, String subject, String content) {
    SecSql sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("SET updateDate = NOW()");
    sql.append(", `subject` = ?", subject);
    sql.append(", content = ?", content);
    sql.append("WHERE id = ?", id);

    MysqlUtil.update(sql);
  }

  public void delete(int id) {
    SecSql sql = new SecSql();
    sql.append("DELETE");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    MysqlUtil.delete(sql);
  }

  public void increaseHit(int id) {
    SecSql sql = new SecSql();

    sql.append("UPDATE article");
    sql.append("SET hit = hit + 1");
    sql.append("WHERE id = ?", id);

    MysqlUtil.update(sql);
  }
}

