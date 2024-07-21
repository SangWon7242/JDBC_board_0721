package com.sbs.java.board;

import com.sbs.java.board.article.Article;
import com.sbs.java.board.container.Container;

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

    while (true) {
      System.out.print("명령) ");
      String cmd = sc.nextLine();

      if(cmd.equals("/usr/article/write")){
        System.out.println("== 게시물 작성 ==");

        System.out.print("제목 : ");
        String subject = sc.nextLine();

        System.out.print("내용 : ");
        String content = sc.nextLine();

        int id = ++lastArticleId;

        Article article = new Article(id, subject, content);

        articles.add(article);

        System.out.printf("%d번 게시물이 추가되었습니다.\n", id);
      }
      else if (cmd.equals("/usr/article/list")) {
        System.out.println("== 게시물 리스트 ==");

        if(articles.isEmpty()) {
          System.out.println("게시물은 존재하지 않습니다.");
          continue;
        }

        for(int i = articles.size() - 1; i >= 0; i--) {
          Article article = articles.get(i);
          System.out.printf("%d / %s\n", article.getId(), article.getSubject());
        }

      }
      else if(cmd.equals("exit")){
        System.out.println("프로그램을 종료합니다.");
        break;
      }
      else {
        System.out.println("잘못 된 명령어 입니다.");
      }
    }

    sc.close();
  }
}
