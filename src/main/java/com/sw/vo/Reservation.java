package com.sw.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Reservation {
  private int no;
  private String status;

  private Date createDate;
  private int count;
  private int totalPrice;
  private int pno;
  private int bno;

}
