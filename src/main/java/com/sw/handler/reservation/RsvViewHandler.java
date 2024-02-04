package com.sw.handler.reservation;


import com.menu.AbstractMenuHandler;
import com.sw.dao.RsvDao;
import com.sw.dto.RsvResponseDto;
import com.sw.vo.Event;
import com.sw.vo.Reservation;
import com.sw.vo.UserDetail;
import com.util.Prompt;

public class RsvViewHandler extends AbstractMenuHandler {

  private RsvDao rsvDao;
  private UserDetail loginSession;

  public RsvViewHandler(RsvDao rsvDao, Prompt prompt, UserDetail loginSession) {
    super(prompt);
    this.loginSession = loginSession;
    this.rsvDao = rsvDao;
  }

  @Override
  protected void action() {
    int no = this.prompt.inputInt("번호? ");
    RsvResponseDto rsv = rsvDao.findBy(no);
    if (rsv == null) {
      System.out.println("예매 번호가 유효하지 않습니다.");
      return;
    }

    System.out.printf("번호: %d\n", rsv.no());
    System.out.printf("제목: %s\n", rsv.title());
    System.out.printf("예약자 이름: %s\n", rsv.name());
    System.out.printf("장소: %s\n", rsv.location());
    System.out.printf("이벤트 시작일: %s\n", rsv.eventStartDate());
    System.out.printf("이벤트 종료일: %s\n", rsv.eventEndDate());
    System.out.printf("티켓: %s\n", rsv.count() + "매");
    System.out.printf("결제금액: %s\n", rsv.totalPrice() + "원");
    System.out.printf("예약현황: %s\n", rsv.status());
    System.out.printf("구매일: %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS\n", rsv.createdDate());
  }
}
