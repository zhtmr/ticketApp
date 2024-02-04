package com.sw.handler.board;


import com.menu.AbstractMenuHandler;
import com.sw.dao.BoardDao;
import com.sw.vo.Event;
import com.sw.vo.Member;
import com.util.Prompt;

import java.util.List;
import java.util.stream.Collectors;

public class BoardListHandler extends AbstractMenuHandler {

  private BoardDao boardDao;

  public BoardListHandler(BoardDao boardDao, Prompt prompt) {
    super(prompt);
    this.boardDao = boardDao;
  }

  @Override
  protected void action() {
    System.out.printf(
        "%-4s\t%10s\t%17s\t%17s\t%15s\t%15s\t%15s\t%14s\t%15s\n",
        "No", "제목", "작성자", "이벤트시작일", "이벤트종료일", "장소", "금액", "예약현황", "작성일");

    List<Event> list = boardDao.findAll();

    for (Event event : list) {
      System.out.printf(
          "%-4d\t%11s\t%16s\t%16s\t%20s\t%15s\t%15s\t%11s\t%23s\n",
          event.getNo(), event.getTitle(), event.getWriter(),
          event.getEventStartDate(), event.getEventEndDate(),
          event.getLocation(), event.getPrice() +"원", event.getCnt() +"명", event.getCreatedDate());
    }
  }

  private String getName(List<Member> members) {
    return members.stream().map(Member::getName).collect(Collectors.joining(", "));
  }

}
