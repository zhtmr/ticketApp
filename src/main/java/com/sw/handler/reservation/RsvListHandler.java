package com.sw.handler.reservation;


import com.menu.AbstractMenuHandler;
import com.sw.dao.RsvDao;
import com.sw.dto.RsvResponseDto;
import com.sw.vo.Event;
import com.sw.vo.Reservation;
import com.sw.vo.UserDetail;
import com.util.Prompt;

import java.util.List;

public class RsvListHandler extends AbstractMenuHandler {

  private RsvDao rsvDao;
  private UserDetail loginSession;

  public RsvListHandler(RsvDao rsvDao, Prompt prompt, UserDetail loginSession) {
    super(prompt);
    this.loginSession = loginSession;
    this.rsvDao = rsvDao;
  }

  @Override
  protected void action() {
    System.out.printf("%-4s\t%10s\t%17s\t%17s\t%19s\t%15s\t%10s\t%15s\n", "번호", "예약자", "제목", "장소", "이벤트시작일",
        "이벤트종료일", "예약현황", "티켓구매날짜");

    List<RsvResponseDto> list = rsvDao.findAll(loginSession);

    for (RsvResponseDto rsv : list) {
      System.out.printf("%-4d\t%10s\t%13s\t%16s\t%16s\t%20s\t%11s\t%14s\n",
          rsv.no(),rsv.name(), rsv.title(), rsv.location(), rsv.eventStartDate(), rsv.eventEndDate(), rsv.status(), rsv.createdDate());
    }
  }

}
