package com.sbs.java.board;

import com.sbs.java.board.container.Container;
import com.sbs.java.board.session.Session;
import com.sbs.java.board.util.Util;
import lombok.Getter;

import java.util.Map;

public class Rq {
  public String url;
  @Getter
  public String urlPath;
  @Getter
  public Map<String, String> params;
  public Session session;
  public String loginedMember;

  public Rq(String url) {
    this.url = url;
    session = Container.session;
    loginedMember = "loginedMember";

    urlPath = Util.getUrlPathFromUrl(this.url);
    params = Util.getParamsFromUrl(this.url);
  }

  public int getIntParam(String paramName, int defaultValue) {
    if (!params.containsKey(paramName)) return defaultValue;

    try {
      return Integer.parseInt(params.get(paramName));
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  public String getParam(String paramName, String defaultValue) {
    if (!params.containsKey(paramName)) return defaultValue;

    return params.get(paramName);
  }

  public void setSessionAttr(String attrName, Object value) {
    session.setAttribute(attrName, value);
  }

  public Object getSessionAttr(String attrName) {
    return session.getAttribute(attrName);
  }

  public boolean hasSessionAttr(String attrName) {
    return session.hasAttribute(attrName);
  }

  public void removeSessionAttr(String attrName) {
    session.removeAttribute(attrName);
  }

  public void login(String attrName, Object value) {
    setSessionAttr(attrName, value);
  }

  public void logout() {
    removeSessionAttr(loginedMember);
  }

  // 로그인 되었으면 true, 안되어 있으면 false
  public boolean isLogined() {
    return hasSessionAttr(loginedMember);
  }

  public boolean isNotLogined() {
    return !isLogined();
  }
}