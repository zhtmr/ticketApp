package com.sw.handler.login;

import com.sw.dao.mysql.LoginDao;
import com.sw.vo.UserDetail;
import com.util.Prompt;


public class LoginHandler {

  private LoginDao loginDao;
  private final Prompt prompt;

  public LoginHandler(LoginDao loginDao, Prompt prompt) {
    this.prompt = prompt;
    this.loginDao = loginDao;
  }

  public UserDetail login() {
    return loginDao.login(prompt.input("로그인 이메일 : "));
  }
}
