package com.sbs.java.board.article;

import com.sbs.java.board.Rq;
import com.sbs.java.board.container.Container;
import com.sbs.java.board.util.MysqlUtil;
import com.sbs.java.board.util.SecSql;

import java.util.List;
import java.util.Map;

public class ArticleController {
  public void doWrite() {
    System.out.println("== 게시물 작성 ==");

    System.out.print("제목 : ");
    String subject = Container.scanner.nextLine();

    System.out.print("내용 : ");
    String content = Container.scanner.nextLine();

    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", `subject` = ?", subject);
    sql.append(", content = ?", content);

    int id = MysqlUtil.insert(sql);

    System.out.printf("%d번 게시물이 추가되었습니다.\n", id);
  }

  public void showList() {
    System.out.println("== 게시물 리스트 ==");

    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");

    List<Map<String, Object>> articlesListMap = MysqlUtil.selectRows(sql);

    if (articlesListMap.isEmpty()) {
      System.out.println("게시물은 존재하지 않습니다.");
      return;
    }

    for (Map<String, Object> articleMap : articlesListMap) {
      System.out.printf("%d / %s\n", (int) articleMap.get("id"), articleMap.get("subject"));
    }
  }

  public void showDetail(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) > 0");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    boolean articleIsEmpty = MysqlUtil.selectRowBooleanValue(sql);

    if (!articleIsEmpty) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    Map<String, Object> articleMap = MysqlUtil.selectRow(sql);

    Article article = new Article(articleMap);

    System.out.println("== 게시물 상세보기 ==");
    System.out.printf("번호 : %d\n", article.getId());
    System.out.printf("작성 날짜 : %s\n", article.getRegDate());
    System.out.printf("수정 날짜 : %s\n", article.getUpdateDate());
    System.out.printf("제목 : %s\n", article.getSubject());
    System.out.printf("내용 : %s\n", article.getContent());
  }

  public void doModify(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) > 0");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    boolean articleIsEmpty = MysqlUtil.selectRowBooleanValue(sql);

    if (!articleIsEmpty) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.println("== 게시물 수정 ==");

    System.out.print("제목 : ");
    String subject = Container.scanner.nextLine();

    System.out.print("내용 : ");
    String content = Container.scanner.nextLine();

    sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("SET updateDate = NOW()");
    sql.append(", `subject` = ?", subject);
    sql.append(", content = ?", content);
    sql.append("WHERE id = ?", id);

    MysqlUtil.update(sql);

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
  }

  public void doDelete(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) > 0");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    boolean articleIsEmpty = MysqlUtil.selectRowBooleanValue(sql);

    if (!articleIsEmpty) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("== %d번 게시물 삭제 ==\n", id);

    sql = new SecSql();
    sql.append("DELETE");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    MysqlUtil.delete(sql);

    System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
  }
}
