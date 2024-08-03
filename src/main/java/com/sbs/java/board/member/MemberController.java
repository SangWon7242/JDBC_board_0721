package com.sbs.java.board.member;

import com.sbs.java.board.container.Container;

public class MemberController {
  private MemberService memberService;

  public MemberController() {
    memberService = Container.memberService;
  }

  public void join() {
    String loginId;
    String loginPw;
    String loginPwConfirm;
    String name;

    System.out.println("== 회원 가입 ==");

    // 로그인 아이디 입력
    while (true) {
      System.out.print("로그인 아이디 : ");
      loginId = Container.scanner.nextLine();

      if(loginId.trim().isEmpty()) {
        System.out.println("로그인 아이디를 입력해주세요.");
        continue;
      }

      Member member = memberService.findByLoginId(loginId);

      if(member != null) {
        System.out.printf("\"%s\"(은)는 이미 사용중인 로그인 아이디입니다.\n", loginId);
        continue;
      }

      break;
    }

    // 비밀번호 입력
    while (true) {
      System.out.print("로그인 패스워드 : ");
      loginPw = Container.scanner.nextLine();

      if(loginPw.trim().isEmpty()) {
        System.out.println("로그인 패스워드를 입력해주세요.");
        continue;
      }

      while (true) {
        System.out.print("로그인 패스워드 확인 : ");
        loginPwConfirm = Container.scanner.nextLine();

        if(loginPwConfirm.trim().isEmpty()) {
          System.out.println("로그인 패스워드 확인을 입력해주세요.");
          continue;
        }

        if(!loginPwConfirm.equals(loginPw)) {
          System.out.println("패스워드가 일치하지 않습니다.");
          continue;
        }

        break;
      }

      break;
    }

    // 이름 입력
    while (true) {
      System.out.print("이름 : ");
      name = Container.scanner.nextLine();

      if(name.trim().isEmpty()) {
        System.out.println("이름을 입력해주세요.");
        continue;
      }

      break;
    }

    memberService.join(loginId, loginPw, name);

    System.out.printf("\"%s\"님 회원 가입 되었습니다.\n", loginId);
  }

  public void login() {
    String loginId;
    String loginPw;
    Member member;

    System.out.println("== 로그인 ==");

    // 로그인 아이디 입력
    while (true) {
      System.out.print("로그인 아이디 : ");
      loginId = Container.scanner.nextLine();

      if(loginId.trim().isEmpty()) {
        System.out.println("로그인 아이디를 입력해주세요.");
        continue;
      }

      member = memberService.findByLoginId(loginId);

      if(member == null) {
        System.out.printf("\"%s\"(은)는 존재하지 않는 로그인 아이디입니다.\n", loginId);
        continue;
      }

      break;
    }

    int loginPwTryMaxCount = 3;
    int loginPwTryCount = 0;

    // 비밀번호 입력
    while (true) {
      if(loginPwTryMaxCount == loginPwTryCount) {
        System.out.println("비밀번호를 확인 후 다시 입력해주세요.");
        return;
      }

      System.out.print("로그인 패스워드 : ");
      loginPw = Container.scanner.nextLine();

      if(loginPw.trim().isEmpty()) {
        System.out.println("로그인 패스워드를 입력해주세요.");
        continue;
      }

      if(!member.getLoginPw().equals(loginPw)) {
        System.out.println("비밀번호가 일치하지 않습니다.");

        loginPwTryCount++;

        System.out.printf("비밀번호 틀린 횟수(%d / %d)\n", loginPwTryCount, loginPwTryMaxCount);

        continue;
      }

      break;
    }

    System.out.printf("\"%s\"님 로그인 되었습니다.\n", member.getLoginId());
  }
}
