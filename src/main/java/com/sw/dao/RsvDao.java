package com.sw.dao;



import com.sw.dto.RsvResponseDto;
import com.sw.vo.Reservation;
import com.sw.vo.UserDetail;

import java.util.List;

public interface RsvDao {

  void add(Reservation rsv);

  int delete(int no);

  List<RsvResponseDto> findAll(UserDetail loginSession);

  RsvResponseDto findBy(int no);

//  int update(Reservation event);

}
