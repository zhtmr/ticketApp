package com.sw.dao;



import com.sw.vo.Event;

import java.util.List;

public interface BoardDao {

  void add(Event event);

  int delete(int no);

  List<Event> findAll();

  Event findBy(int no);

  int update(Event event);

}
