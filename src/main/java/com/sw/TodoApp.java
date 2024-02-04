package com.sw;

import com.menu.MenuGroup;
import com.sw.dao.BoardDao;
import com.sw.dao.RsvDao;
import com.sw.dao.mysql.BoardDaoImpl;
import com.sw.dao.mysql.LoginDao;
import com.sw.dao.mysql.RsvDaoImpl;
import com.sw.handler.board.*;
import com.sw.handler.login.LoginHandler;
import com.sw.handler.reservation.*;
import com.sw.vo.UserDetail;
import com.util.AnsiEscape;
import com.util.Logo;
import com.util.Prompt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TodoApp {
  Prompt prompt = new Prompt(System.in);
  BoardDao boardDao;
  RsvDao rsvDao;
  LoginDao loginDao;
  MenuGroup mainMenu;
  UserDetail loginSession;
  Connection con = null;

  TodoApp() {
    prepareDatabase();
    printLogo();
    prepareLogin();
    prepareMenu();
  }

  private void printLogo() {
    Logo.print();
  }

  private void prepareLogin() {
    while (true) {
      loginSession = new LoginHandler(loginDao, prompt).login();
      if (loginSession.isLogin()) {
        break;
      } else {
        System.out.println("다시 로그인 해 주세요");
      }
    }
  }

  private void prepareMenu() {

    if (loginSession.getType() == 1) {   // 관리자
      mainMenu = MenuGroup.getInstance("이벤트 관리 시스템 ("+ AnsiEscape.ANSI_BOLD_RED +loginSession.getName() + "님 로그인 중" + AnsiEscape.ANSI_CLEAR+ ")");
      MenuGroup todoListMenu = mainMenu.addGroup("행사 관리");
      todoListMenu.addItem("등록", new BoardAddHandler(boardDao, prompt));
      todoListMenu.addItem("상세보기", new BoardViewHandler(boardDao, prompt));
      todoListMenu.addItem("변경", new BoardModifyHandler(boardDao, prompt));
      todoListMenu.addItem("삭제", new BoardDeleteHandler(boardDao, prompt));
      todoListMenu.addItem("목록", new BoardListHandler(boardDao, prompt));

    } else if (loginSession.getType() == 2) {   // 일반회원
      mainMenu = MenuGroup.getInstance("행사 예매 페이지 (" + AnsiEscape.ANSI_BOLD_RED + loginSession.getName() + "님 로그인 중" + AnsiEscape.ANSI_CLEAR+ ")");
      MenuGroup reservation = mainMenu.addGroup("공연 예매하기");
      reservation.addItem("예매하기", new RsvAddHandler(rsvDao, prompt, con, loginSession));
      reservation.addItem("상세보기", new RsvViewHandler(rsvDao, prompt, loginSession));
      reservation.addItem("예매 취소", new RsvDeleteHandler(rsvDao, prompt, loginSession));
      reservation.addItem("예매 내역", new RsvListHandler(rsvDao, prompt, loginSession));
    }

  }

  private void prepareDatabase() {
    try {
      con = DriverManager.getConnection(
          //          "jdbc:mysql://db-ld27v-kr.vpc-pub-cdb.ntruss.com/studydb",
          "jdbc:mysql://localhost/studydb", "study", "Bitcamp!@#123");
    } catch (SQLException e) {
      System.out.println("통신 오류!");
      e.printStackTrace();
    }
    loginDao = new LoginDao(con);
    boardDao = new BoardDaoImpl(con);
    rsvDao = new RsvDaoImpl(con);
  }

  void run() {
    while (true) {
      try {
        mainMenu.execute(prompt);
        prompt.close();
        break;
      } catch (Exception e) {
        System.out.println("main() 예외 발생");
      }
    }
  }

  public static void main(String[] args) {
    new TodoApp().run();
  }
}
