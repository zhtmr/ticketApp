package com.sw.handler.board;


import com.menu.AbstractMenuHandler;
import com.sw.dao.BoardDao;
import com.sw.vo.Event;
import com.util.Prompt;

import java.util.Date;

public class BoardAddHandler extends AbstractMenuHandler {

  private BoardDao boardDao;

  public BoardAddHandler(BoardDao boardDao, Prompt prompt) {
    super(prompt);
    this.boardDao = boardDao;
  }

  @Override
  protected void action() {
    Event event = new Event();
    event.setTitle(prompt.input("이벤트 제목? "));
    event.setContent(prompt.input("내용? "));
    event.setWriter(this.prompt.input("작성자? "));
    event.setEventStartDate(prompt.inputDate("시작일? (ex: 2023-12-25) "));
    event.setEventEndDate(prompt.inputDate("종료일? (ex: 2024-3-5) "));
    event.setLocation(prompt.input("장소? "));
    event.setPrice(prompt.inputInt("금액? "));
    event.setCreatedDate(new Date());

    boardDao.add(event);
  }
}
