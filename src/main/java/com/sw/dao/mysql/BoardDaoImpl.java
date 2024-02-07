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
    try (PreparedStatement pstmt = con.prepareStatement(
        "insert into tb_event(title, writer, content, event_start_date, event_end_date, location, price) values (?, ?, ?,?,?, ?, ?)")) {

      pstmt.setString(1, event.getTitle());
      pstmt.setString(2, event.getWriter());
      pstmt.setString(3, event.getContent());
      pstmt.setDate(4, (Date) event.getEventStartDate());
      pstmt.setDate(5, (Date) event.getEventEndDate());
      pstmt.setString(6, event.getLocation());
      pstmt.setInt(7, event.getPrice());

      pstmt.executeUpdate();

    } catch (Exception e) {
      throw new DaoException("데이터 입력 오류", e);
    }
  }

  @Override
  public int delete(int no) {
    try (PreparedStatement pstmt = con.prepareStatement("delete from tb_event where no=?")) {
      pstmt.setInt(1, no);
      return pstmt.executeUpdate();

    } catch (Exception e) {
      throw new DaoException("데이터 삭제 오류", e);
    }
  }

  @Override
  public List<Event> findAll() {
    try {
      ArrayList<Event> list;
      try (PreparedStatement pstmt = con.prepareStatement(
          "select te.no, te.title, te.writer, te.content, te.event_start_date, te.event_end_date, te.created_date, te.location, te.price, count(tr.status)cnt from tb_event te left outer join tb_res tr on te.no = tr.bno group by te.no")) {
        try (ResultSet rs = pstmt.executeQuery()) {
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
      }

      return list;
    } catch (Exception e) {
      throw new DaoException("데이터 가져오기 오류", e);
    }
  }


  @Override
  public Event findBy(int no) {
    try {
      try (PreparedStatement pstmt = con.prepareStatement(
          "select te.no, te.title, te.writer, te.content, te.event_start_date, te.event_end_date, te.location, te.price, te.created_date from tb_event te where te.no =?")) {
        pstmt.setInt(1, no);
        ResultSet rs = pstmt.executeQuery();
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
    try (PreparedStatement pstmt = con.prepareStatement(
        "update tb_event set title=?, content=?, event_start_date=?, event_end_date=?, location=?, price=? where no=?")) {
      pstmt.setString(1, event.getTitle());
      pstmt.setString(2, event.getContent());
      pstmt.setDate(3, (Date) event.getEventStartDate());
      pstmt.setDate(4, (Date) event.getEventEndDate());
      pstmt.setString(5, event.getLocation());
      pstmt.setInt(6, event.getPrice());
      pstmt.setInt(7, event.getNo());
      return pstmt.executeUpdate();

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
