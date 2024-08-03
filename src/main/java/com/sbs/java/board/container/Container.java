package com.sbs.java.board.container;

import com.sbs.java.board.article.ArticleController;
import com.sbs.java.board.member.MemberController;

import java.util.Scanner;

public class Container {
  public static Scanner scanner;

  public static MemberController memberController;
  public static ArticleController articleController;

  static {
    scanner = new Scanner(System.in);

    memberController = new MemberController();
    articleController = new ArticleController();
  }
}
