package com.sw.handler.reservation;


import com.menu.AbstractMenuHandler;
import com.sw.dao.BoardDao;
import com.sw.vo.Event;
import com.util.Prompt;

public class RsvModifyHandler extends AbstractMenuHandler {

  private BoardDao boardDao;

  public RsvModifyHandler(BoardDao boardDao, Prompt prompt) {
    super(prompt);
    this.boardDao = boardDao;
  }

  @Override
  protected void action() {

  }

}
