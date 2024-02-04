package com.sw.handler.reservation;


import com.menu.AbstractMenuHandler;
import com.sw.dao.RsvDao;
import com.sw.dao.mysql.BoardDaoImpl;
import com.sw.vo.Event;
import com.sw.vo.Reservation;
import com.sw.vo.UserDetail;
import com.util.Prompt;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class RsvAddHandler extends AbstractMenuHandler {

  private RsvDao rsvDao;
  private List<Event> eventList;
  private UserDetail loginSession;

  public RsvAddHandler(RsvDao rsvDao, Prompt prompt, Connection con, UserDetail loginSession) {
    super(prompt);
    this.loginSession = loginSession;
    this.eventList = new BoardDaoImpl(con).findAll();
    this.rsvDao = rsvDao;
  }

  @Override
  protected void action() {
    Reservation rsv = new Reservation();
    System.out.println("------------------ 공연 목록 ------------------");
    for (Event value : eventList) {
      System.out.printf("🎬[ %d ] %s | %s | %d원 | %s", value.getNo(), value.getTitle(), value.getLocation(), value.getPrice(), value.getEventStartDate());
      System.out.println();
    }
    System.out.println("-----------------------------------------------");

    int price;
    while (true) {
      int selected = prompt.inputInt("어떤 공연을 예매 하시겠습니까? ");
      boolean test = eventList.stream().map(Event::getNo).anyMatch(no -> no.equals(selected));
      if (test) {
        price = eventList.stream().filter(event -> event.getNo() == selected).map(Event::getPrice).findAny().get();
        rsv.setBno(selected);
        break;
      }
      System.out.println("‼️해당하는 공연이 없습니다. 다시 선택해주세요");
    }

    int count = prompt.inputInt("티켓 구매 갯수? ");
    rsv.setCount(count);
    rsv.setTotalPrice(count * price);
    rsv.setPno(loginSession.getNo());
    rsv.setStatus("완료");
    rsv.setCreateDate(new Date());

    rsvDao.add(rsv);
  }
}
