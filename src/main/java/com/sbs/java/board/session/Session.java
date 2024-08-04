package com.sbs.java.board.session;

import java.util.HashMap;
import java.util.Map;

public class Session {
  private Map<String, Object> sessionStore;

  public Session() {
    sessionStore = new HashMap<>();
  }

  // 세션 데이터 저장
  public void setAttribute(String attrName, Object value) {
    sessionStore.put(attrName, value);
  }

  // 세션 데이터 가져오기
  public Object getAttribute(String attrName) {
    return sessionStore.get(attrName);
  }

  // 세션 데이터 유무확인
  public boolean hasAttribute(String attrName) {
    return sessionStore.containsKey(attrName);
  }

  // 세션 데이터 삭제
  public void removeAttribute(String attrName) {
    sessionStore.remove(attrName);
  }
}
