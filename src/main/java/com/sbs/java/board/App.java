package com.sbs.java.board;

import com.sbs.java.board.article.Article;
import com.sbs.java.board.container.Container;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
  public List<Article> articles;
  public int lastArticleId;

  public App() {
    articles = new ArrayList<>();
    lastArticleId = 0;
  }

  void run() {
    Scanner sc = Container.scanner;

    try {
      while (true) {
        System.out.print("명령) ");
        String cmd = sc.nextLine();

        Rq rq = new Rq(cmd);

        // DB 연결시작
        String url = "jdbc:mysql://127.0.0.1:3306/JDBC_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
        String user = "sbsst"; // 계정이 root면 "root" 입력
        String password = "sbs123414"; // 비밀번호가 없다면 ""(공백) 입력

        Connection conn = null;
        PreparedStatement pstat = null;

        try {
          // MySQL JDBC 드라이버 로드
          Class.forName("com.mysql.cj.jdbc.Driver");

          // 데이터베이스 연결
          conn = DriverManager.getConnection(url, user, password);

          // 액션메서드 시작
          doAction(conn, pstat, rq, sc);

        } catch (ClassNotFoundException e) {
          System.out.println("JDBC 드라이버를 찾을 수 없습니다.");
          e.printStackTrace();
        } catch (SQLException e) {
          System.out.println("데이터베이스 연결 실패!");
          e.printStackTrace();
        } finally {
          // 연결 닫기
          try {
            if (conn != null && !conn.isClosed()) {
              conn.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
        // DB 연결해제
      }
    } finally {
      sc.close();
    }
  }

  private void doAction(Connection conn, PreparedStatement pstat, Rq rq, Scanner sc) {
    if (rq.getUrlPath().equals("/usr/article/write")) {
      System.out.println("== 게시물 작성 ==");

      System.out.print("제목 : ");
      String subject = sc.nextLine();

      System.out.print("내용 : ");
      String content = sc.nextLine();

      int id = ++lastArticleId;

      Article article = new Article(id, subject, content);

      try {

        String sql = "INSERT INTO article";
        sql += " SET regDate = NOW()";
        sql += ", updateDate = NOW()";
        sql += ", `subject` = '%s'".formatted(subject);
        sql += ", content = '%s';".formatted(content);

        System.out.println("sql : " + sql);

        pstat = conn.prepareStatement(sql);
        pstat.executeUpdate();

      } catch (SQLException e) {
        System.out.println("데이터베이스 연결 실패!");
        e.printStackTrace();
      } finally {
        // 자원 정리
        try {
          if (pstat != null && !pstat.isClosed()) {
            pstat.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

      System.out.printf("%d번 게시물이 추가되었습니다.\n", article.getId());
    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      System.out.println("== 게시물 리스트 ==");

      if (articles.isEmpty()) {
        System.out.println("게시물은 존재하지 않습니다.");
        return;
      }

      for (int i = articles.size() - 1; i >= 0; i--) {
        Article article = articles.get(i);
        System.out.printf("%d / %s\n", article.getId(), article.getSubject());
      }

    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      int id = rq.getIntParam("id", 0);

      if(id == 0) {
        System.out.println("id를 올바르게 입력해주세요.");
        return;
      }

      System.out.println("== 게시물 수정 ==");

      System.out.print("제목 : ");
      String subject = sc.nextLine();

      System.out.print("내용 : ");
      String content = sc.nextLine();

      try {
        String sql = "UPDATE article";
        sql += " SET updateDate = NOW()";
        sql += ", `subject` = '%s'".formatted(subject);
        sql += ", content = '%s'".formatted(content);
        sql += " WHERE id = %d;".formatted(id);

        pstat = conn.prepareStatement(sql);
        pstat.executeUpdate(sql);

      } catch (SQLException e) {
        System.out.println("데이터베이스 연결 실패!");
        e.printStackTrace();
      } finally {
        try {
          if (pstat != null && !pstat.isClosed()) {
            pstat.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

      System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
    }
    else if (rq.getUrlPath().equals("exit")) {
      System.out.println("프로그램을 종료합니다.");
      System.exit(0);
    } else {
      System.out.println("잘못 된 명령어 입니다.");
    }
  }
}
