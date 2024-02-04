package com.sw.dao.mysql;

import com.sw.vo.UserDetail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class LoginDao {
  Connection con;

  public LoginDao(Connection con) {
    this.con = con;
  }

  public UserDetail login(String email) {
    UserDetail loginUser = new UserDetail();
    try {
      String sql =
          String.format("select te.no, te.email, te.name, te.type from tb_member te where te.email='%s'",
              email);
      try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

        if (rs.next()) {
          loginUser.setEmail(rs.getString("email"));
          loginUser.setNo(rs.getInt("no"));
          loginUser.setName(rs.getString("name"));
          loginUser.setType(rs.getInt("type"));
          loginUser.setLogin(true);
        }
        progressBar();
        return loginUser;
      }
    } catch (Exception e) {
      return null;
    }
  }

  private void progressBar() {
    int total = 1000;
    for (int i = 0; i < total; i++) {
      double percent = (double) i / total * 100;
      int mod = (int) (percent % 10);
      StringBuilder sb = new StringBuilder();
      for (int j = 0; j < 100; j++) {
        if (j < mod) {
          sb.append("█");
        } else {
          sb.append(" ");
        }

      }
      sb.append(String.format("%.2f", percent));
      System.out.print(sb);
      System.out.print("\r");
      try {
        TimeUnit.MILLISECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
