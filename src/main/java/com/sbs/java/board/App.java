package com.sbs.java.board;

import com.sbs.java.board.article.Article;
import com.sbs.java.board.article.ArticleController;
import com.sbs.java.board.container.Container;
import com.sbs.java.board.member.MemberController;
import com.sbs.java.board.util.MysqlUtil;
import com.sbs.java.board.util.SecSql;

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
        doAction(rq);
      }
    } finally {
      sc.close();
    }
  }

  private void doAction(Rq rq) {
    MemberController memberController = Container.memberController;
    ArticleController articleController = Container.articleController;

    if (rq.getUrlPath().equals("/usr/article/write")) {
      articleController.doWrite();
    } else if (rq.getUrlPath().equals("/usr/article/list")) {
      articleController.showList();
    } else if (rq.getUrlPath().equals("/usr/article/detail")) {
      articleController.showDetail(rq);
    } else if (rq.getUrlPath().equals("/usr/article/modify")) {
      articleController.doModify(rq);
    } else if (rq.getUrlPath().equals("/usr/article/delete")) {
      articleController.doDelete(rq);
    } else if (rq.getUrlPath().equals("/usr/member/join")) {
      memberController.join();
    } else if (rq.getUrlPath().equals("exit")) {
      System.out.println("프로그램을 종료합니다.");
      System.exit(0);
    } else {
      System.out.println("잘못 된 명령어 입니다.");
    }
  }
}
