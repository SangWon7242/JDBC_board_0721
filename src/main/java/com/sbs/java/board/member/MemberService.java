package com.sbs.java.board.member;

import com.sbs.java.board.container.Container;

public class MemberService {
  private MemberRepository memberRepository;

  public MemberService() {
    memberRepository = Container.memberRepository;
  }

  public Member findByLoginId(String loginId) {
    return memberRepository.findByLoginId(loginId);
  }

  public void join(String loginId, String loginPw, String name) {
    memberRepository.join(loginId, loginPw, name);
  }
}
