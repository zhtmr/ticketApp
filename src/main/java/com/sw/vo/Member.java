package com.sw.vo;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Member {
  private int no;
  private String name;
  private String email;

  public Member() {
  }

  public Member(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public Member(int no, String name, String email) {
    this.no = no;
    this.name = name;
    this.email = email;
  }

  @Override
  public String toString() {
    return name + '[' +  email + ']';
  }
}
