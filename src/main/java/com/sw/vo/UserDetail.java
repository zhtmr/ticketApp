package com.sw.vo;

import lombok.Getter;
import lombok.Setter;

public class UserDetail {
  @Setter
  @Getter
  int no;
  @Setter
  @Getter
  String email;
  @Setter
  @Getter
  Integer type;
  @Setter
  @Getter
  String name;
  boolean isLogin = false;

  public boolean isLogin() {
    return isLogin;
  }

  public void setLogin(boolean login) {
    isLogin = login;
  }

}
