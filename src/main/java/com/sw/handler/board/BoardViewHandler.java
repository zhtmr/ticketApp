package com.sw.handler.board;


import com.menu.AbstractMenuHandler;
import com.sw.dao.BoardDao;
import com.sw.vo.Event;
import com.util.Prompt;

public class BoardViewHandler extends AbstractMenuHandler {

  private BoardDao boardDao;

  public BoardViewHandler(BoardDao boardDao, Prompt prompt) {
    super(prompt);
    this.boardDao = boardDao;
  }

  @Override
  protected void action() {
    int no = this.prompt.inputInt("번호? ");
    Event event = boardDao.findBy(no);
    if (event == null) {
      System.out.println("게시글 번호가 유효하지 않습니다.");
      return;
    }

    System.out.printf("번호: %d\n", event.getNo());
    System.out.printf("제목: %s\n", event.getTitle());
    System.out.printf("작성자: %s\n", event.getWriter());
    System.out.printf("내용: %s\n", event.getContent());
    System.out.printf("이벤트 시작일: %s\n", event.getEventStartDate());
    System.out.printf("이벤트 종료일: %s\n", event.getEventEndDate());
    System.out.printf("장소: %s\n", event.getLocation());
    System.out.printf("금액: %d\n", event.getPrice());
    System.out.printf("작성일: %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS\n", event.getCreatedDate());
  }
}
