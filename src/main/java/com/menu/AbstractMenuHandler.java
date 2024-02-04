package com.menu;

import com.util.AnsiEscape;
import com.util.Prompt;

public abstract class AbstractMenuHandler implements MenuHandler {

  protected Prompt prompt;
  protected Menu menu;

  public AbstractMenuHandler(Prompt prompt) {
    this.prompt = prompt;
  }

  @Override
  public void action(Menu menu) {
    this.printMenuTitle(menu.getTitle());
    this.menu = menu; // 서브클래스 구현 시 사용할 일이 있다면 쓸 수 있도록 보관해 둔다.

    // Menu 를 실행할 때 이 메소드가 호출되면 즉시 서브 클래스의 다음 메소드를 호출한다.
    this.action();
  }

  private void printMenuTitle(String title) {
    System.out.printf(AnsiEscape.ANSI_BOLD + "[%s]\n" + AnsiEscape.ANSI_CLEAR, title);
  }

  protected abstract void action(); // 서브클래스가 구현해야 할 메소드. 외부에서 호출할 메소드가 아니다(protected)
}
