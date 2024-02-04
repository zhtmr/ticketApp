package com.sw.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Event {

  private int no;
  private String title;
  private String content;
  private String writer;
  private Date eventStartDate;
  private Date eventEndDate;
  private Date createdDate;
  private String location;
  private int price;
  private int cnt;

}
