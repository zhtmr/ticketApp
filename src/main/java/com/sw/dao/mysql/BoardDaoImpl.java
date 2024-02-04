package com.sw.dao.mysql;

import com.sw.dao.BoardDao;
import com.sw.dao.DaoException;
import com.sw.vo.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDaoImpl implements BoardDao {
  Connection con;

  public BoardDaoImpl(Connection con) {
    this.con = con;
  }

  @Override
  public void add(Event event) {
    Statement stmt = null;

    try {
      con.setAutoCommit(false);
      stmt = con.createStatement();

      String sql = String.format(
          "insert into tb_event(title, writer, content, event_start_date, event_end_date, location, price) values ('%s', '%s', '%s','%s','%s', '%s', %d)",
          event.getTitle(), event.getWriter(), event.getContent(), event.getEventStartDate(),
          event.getEventEndDate(), event.getLocation(), event.getPrice());

      stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

      con.commit();
    } catch (Exception e) {
      try {
        con.rollback();
      } catch (SQLException ex) {
        throw new DaoException("롤백 오류", ex);
      }
      throw new DaoException("데이터 입력 오류", e);
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
      return stmt.executeUpdate(String.format("delete from tb_event where no=%d", no));

    } catch (Exception e) {
      throw new DaoException("데이터 삭제 오류", e);
    }
  }

  @Override
  public List<Event> findAll() {
    try {
      ArrayList<Event> list;
      try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("""
          select
              te.no,
              te.title,
              te.writer,
              te.content,
              te.event_start_date,
              te.event_end_date,
              te.created_date,
              te.location,
              te.price,
              count(tr.status) cnt
          from  tb_event te
              left outer join tb_res tr
                  on te.no = tr.bno
          group by te.no;""")) {
        list = new ArrayList<>();

        while (rs.next()) {
          Event event = new Event();
          event.setNo(rs.getInt("no"));
          event.setTitle(rs.getString("title"));
          event.setWriter(rs.getString("writer"));
          event.setContent(rs.getString("content"));
          event.setEventStartDate(rs.getDate("event_start_date"));
          event.setEventEndDate(rs.getDate("event_end_date"));
          event.setLocation(rs.getString("location"));
          event.setPrice(rs.getInt("price"));
          event.setCnt(rs.getInt("cnt"));
          event.setCreatedDate(rs.getDate("created_date"));
          list.add(event);
        }
      }

      return list;
    } catch (Exception e) {
      throw new DaoException("데이터 가져오기 오류", e);
    }
  }


  @Override
  public Event findBy(int no) {
    try {
      try (Statement stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery("""
            select 
                te.no,
                te.title,
                te.writer,
                te.content,
                te.event_start_date,
                te.event_end_date,
                te.location,
                te.price,
                te.created_date
            from tb_event te 
            where te.no = """ + no)) {

        if (rs.next()) {
          Event event = new Event();
          event.setNo(rs.getInt("no"));
          event.setTitle(rs.getString("title"));
          event.setWriter(rs.getString("writer"));
          event.setContent(rs.getString("content"));
          event.setEventStartDate(rs.getDate("event_start_date"));
          event.setEventEndDate(rs.getDate("event_end_date"));
          event.setLocation(rs.getString("location"));
          event.setPrice(rs.getInt("price"));
          event.setCreatedDate(rs.getDate("created_date"));

          return event;
        }
      }
      return null;
    } catch (Exception e) {
      throw new DaoException("데이터 가져오기 오류", e);
    }
  }

  @Override
  public int update(Event event) {
    try (Statement stmt = con.createStatement()) {
      String updateBoardSql = String.format(
          "update tb_event " + "set " + "title='%s'," + " content='%s'," +  " event_start_date='%s'," + " event_end_date='%s'," +  "location='%s'" + "price=%d"+ " where no=%d",
          event.getTitle(), event.getContent(), event.getEventStartDate(),
          event.getEventEndDate(), event.getLocation(),event.getPrice(), event.getNo());
      return stmt.executeUpdate(updateBoardSql);

    } catch (Exception e) {
      try {
        con.rollback();
      } catch (SQLException ex) {
        throw new RuntimeException(ex);
      }
      throw new DaoException("데이터 변경 오류", e);
    }
  }
}
