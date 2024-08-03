package com.sbs.java.board.article;

import com.sbs.java.board.Rq;
import com.sbs.java.board.container.Container;

import java.util.List;

public class ArticleController {
  private ArticleService articleService;

  public ArticleController() {
    articleService = Container.articleService;
  }

  public void doWrite() {
    System.out.println("== 게시물 작성 ==");

    System.out.print("제목 : ");
    String subject = Container.scanner.nextLine();

    if(subject.isEmpty()) {
      System.out.println("제목을 입력해주세요.");
      return;
    }

    System.out.print("내용 : ");
    String content = Container.scanner.nextLine();

    if(content.isEmpty()) {
      System.out.println("내용을 입력해주세요.");
      return;
    }

    int id = articleService.write(subject, content);

    System.out.printf("%d번 게시물이 추가되었습니다.\n", id);
  }

  public void showList() {
    System.out.println("== 게시물 리스트 ==");

    List<Article> articles = articleService.getArticles();

    if (articles.isEmpty()) {
      System.out.println("게시물은 존재하지 않습니다.");
      return;
    }

    for (Article article : articles) {
      System.out.printf("%d / %s\n", article.getId(), article.getSubject());
    }
  }

  public void showDetail(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    Article article = articleService.findByArticleId(id);

    if (article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

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

    Article article = articleService.findByArticleId(id);

    if (article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.println("== 게시물 수정 ==");

    System.out.print("제목 : ");
    String subject = Container.scanner.nextLine();

    if(subject.isEmpty()) {
      System.out.println("제목을 입력해주세요.");
      return;
    }

    System.out.print("내용 : ");
    String content = Container.scanner.nextLine();

    if(content.isEmpty()) {
      System.out.println("내용을 입력해주세요.");
      return;
    }

    articleService.update(id, subject, content);

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
  }

  public void doDelete(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if (id == 0) {
      System.out.println("id를 올바르게 입력해주세요.");
      return;
    }

    Article article = articleService.findByArticleId(id);

    if (article == null) {
      System.out.printf("%d번 게시물은 존재하지 않습니다.\n", id);
      return;
    }

    System.out.printf("== %d번 게시물 삭제 ==\n", id);

    articleService.delete(id);

    System.out.printf("%d번 게시물이 삭제되었습니다.\n", id);
  }
}
