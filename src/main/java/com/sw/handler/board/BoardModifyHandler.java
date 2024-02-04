package com.sw.handler.board;


import com.menu.AbstractMenuHandler;
import com.sw.dao.BoardDao;
import com.sw.vo.Event;
import com.util.Prompt;

public class BoardModifyHandler extends AbstractMenuHandler {

  private BoardDao boardDao;

  public BoardModifyHandler(BoardDao boardDao, Prompt prompt) {
    super(prompt);
    this.boardDao = boardDao;
  }

  @Override
  protected void action() {
    int no = this.prompt.inputInt("번호? ");
    Event oldEvent = boardDao.findBy(no);
    if (oldEvent == null) {
      System.out.println("게시글 번호가 유효하지 않습니다.");
      return;
    }

    Event event = new Event();
    event.setNo(oldEvent.getNo());
    event.setTitle(this.prompt.input("제목(%s)? ", oldEvent.getTitle()));
    event.setContent(this.prompt.input("내용(%s)? ", oldEvent.getContent()));
    event.setEventStartDate(this.prompt.inputDate("이벤트 시작일(%s)? ", oldEvent.getEventStartDate()));
    event.setEventEndDate(this.prompt.inputDate("이벤트 종료일(%s)? ", oldEvent.getEventEndDate()));
    event.setLocation(this.prompt.input("장소(%s)? ", oldEvent.getLocation()));
    event.setPrice(this.prompt.inputInt("금액(%d)? ", oldEvent.getPrice()));
    event.setCreatedDate(oldEvent.getCreatedDate());

    boardDao.update(event);
    System.out.println("게시글을 변경했습니다.");
  }

}
