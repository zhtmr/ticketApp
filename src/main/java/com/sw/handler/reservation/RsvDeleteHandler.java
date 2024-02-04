package com.sw.handler.reservation;


import com.menu.AbstractMenuHandler;
import com.sw.dao.BoardDao;
import com.sw.dao.RsvDao;
import com.sw.vo.UserDetail;
import com.util.Prompt;

public class RsvDeleteHandler extends AbstractMenuHandler {

  private RsvDao rsvDao;
  private UserDetail loginSession;

  public RsvDeleteHandler(RsvDao rsvDao, Prompt prompt, UserDetail loginSession) {
    super(prompt);
    this.loginSession = loginSession;
    this.rsvDao = rsvDao;
  }

  @Override
  protected void action() {
    int no = this.prompt.inputInt("번호? ");
    if (rsvDao.delete(no) == 0) {
      System.out.println("번호가 유효하지 않습니다.");
    } else {
      System.out.println("삭제했습니다.");
    }
  }
}
