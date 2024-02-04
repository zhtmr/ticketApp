package com.sw.dao.mysql;

import com.sw.dao.DaoException;
import com.sw.dao.RsvDao;
import com.sw.dto.RsvResponseDto;
import com.sw.vo.Reservation;
import com.sw.vo.UserDetail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RsvDaoImpl implements RsvDao {
  Connection con;

  public RsvDaoImpl(Connection con) {
    this.con = con;
  }

  @Override
  public void add(Reservation rsv) {
    Statement stmt = null;
    try {
      stmt = con.createStatement();

      String sql = String.format(
          "insert into tb_res(bno, pno, status, count, total_price) values (%d, %d, '%s', %d, %d)",
          rsv.getBno(), rsv.getPno(), rsv.getStatus(), rsv.getCount(), rsv.getTotalPrice());

      stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
      System.out.println("😀예매 완료!");
    } catch (Exception e) {
      System.out.println("이미 예매한 항목입니다.");
    } finally {
      if (stmt != null) {
        try {
          stmt.close();
        } catch (SQLException e) {
          throw new DaoException("stmt close 오류", e);
        }
      }
    }
  }

  @Override
  public int delete(int no) {
    try {
      Statement stmt = con.createStatement();
      return stmt.executeUpdate(String.format("delete from tb_res where no=%d", no));

    } catch (Exception e) {
      throw new DaoException("데이터 삭제 오류", e);
    }
  }

  @Override
  public List<RsvResponseDto> findAll(UserDetail loginSession) {
    try {
      ArrayList<RsvResponseDto> list;
      String sql = String.format(
          "select tr.no, tm.name, te.title, te.location, te.event_start_date, te.event_end_date, tr.status, tr.created_date from tb_res tr left outer join tb_event te on tr.bno = te.no left outer join tb_member tm on tr.pno = tm.no where tm.email='%s'",
          loginSession.getEmail());
      try (Statement stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery(sql)) {

        list = new ArrayList<>();
        while (rs.next()) {
          RsvResponseDto rsvResponseDto =
              RsvResponseDto.builder().no(rs.getInt("no")).name(rs.getString("name"))
                  .title(rs.getString("title")).eventStartDate(rs.getDate("event_start_date"))
                  .eventEndDate(rs.getDate("event_end_date")).status(rs.getString("status"))
                  .createdDate(rs.getDate("created_date")).location(rs.getString("location")).build();
          list.add(rsvResponseDto);
        }
      }
      return list;
    } catch (Exception e) {
      throw new DaoException("데이터 가져오기 오류", e);
    }
  }


  @Override
  public RsvResponseDto findBy(int no) {
    try {
      try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("""
                                                                                        select
                                                                                            tr.no,
                                                                                            tm.name,
                                                                                            te.title,
                                                                                            te.event_start_date,
                                                                                            te.event_end_date,
                                                                                            te.location,
                                                                                            tr.count,
                                                                                            tr.total_price,
                                                                                            tr.status,
                                                                                            tr.created_date
                                                                                        from tb_res tr 
                                                                                        left join tb_event te 
                                                                                          on tr.bno = te.no
                                                                                        left join tb_member tm
                                                                                          on tr.pno = tm.no
                                                                                        where tr.no = """ + no)) {

        if (rs.next()) {
          return RsvResponseDto.builder().no(rs.getInt("no")).name(rs.getString("name"))
              .title(rs.getString("title")).eventStartDate(rs.getDate("event_start_date"))
              .eventEndDate(rs.getDate("event_end_date")).location(rs.getString("location"))
              .count(rs.getInt("count")).totalPrice(rs.getInt("total_price"))
              .status(rs.getString("status")).createdDate(rs.getDate("created_date")).build();
        }
      }
      return null;
    } catch (Exception e) {
      throw new DaoException("데이터 가져오기 오류", e);
    }
  }

}
