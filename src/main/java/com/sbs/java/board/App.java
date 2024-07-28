package com.sbs.java.board;

import com.sbs.java.board.article.Article;
import com.sbs.java.board.container.Container;
import com.sbs.java.board.util.MysqlUtil;
import com.sbs.java.board.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class App {
  private static boolean isDevMode() {
    // 이 부분을 false로 바꾸면 production 모드 이다.
    // true는 개발자 모드이다.(개발할 때 좋다.)
    return true;
  }

  void run() {
    Scanner sc = Container.scanner;

    try {
      while (true) {
        System.out.print("명령) ");
        String cmd = sc.nextLine();

        Rq rq = new Rq(cmd);

        // DB 세팅
        MysqlUtil.setDBInfo("localhost", "sbsst", "sbs123414", "JDBC_board");
        MysqlUtil.setDevMode(isDevMode());
        // DB 끝

        // 액션메서드 시작
        doAction(rq, sc);
      }
    } finally {
      sc.close();
    }
  }

  private void doAction(Rq rq, Scanner sc) {
    if (rq.getUrlPath().equals("/usr/article/write")) {
      System.out.println("== 게시물 작성 ==");

      System.out.print("제목 : ");
      String subject = sc.nextLine();

      System.out.print("내용 : ");
      String content = sc.nextLine();

      SecSql sql = new SecSql();
      sql.append("INSERT INTO article");
      sql.append("SET regDate = NOW()");
      sql.append(", updateDate = NOW()");
      sql.append(", `subject` = ?", subject);
      sql.append(", content = ?", content);

      int id = MysqlUtil.insert(sql);

      System.out.printf("%d번 게시물이 추가되었습니다.\n", id);
    } else if (rq.getUrlPath().equals("/usr/article/list")) {
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
        System.out.printf("%d / %s\n", (int) articleMap.get("id"), (String) articleMap.get("subject"));
      }

    } else if (rq.getUrlPath().equals("/usr/article/detail")) {
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

    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
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
      String subject = sc.nextLine();

      System.out.print("내용 : ");
      String content = sc.nextLine();

      sql = new SecSql();
      sql.append("UPDATE article");
      sql.append("SET updateDate = NOW()");
      sql.append(", `subject` = ?", subject);
      sql.append(", content = ?", content);
      sql.append("WHERE id = ?", id);

      MysqlUtil.update(sql);

      System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
    } else if (rq.getUrlPath().equals("/usr/article/delete")) {
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
    } else if (rq.getUrlPath().equals("exit")) {
      System.out.println("프로그램을 종료합니다.");
      System.exit(0);
    } else {
      System.out.println("잘못 된 명령어 입니다.");
    }
  }
}
