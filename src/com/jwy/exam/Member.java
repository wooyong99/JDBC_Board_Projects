package com.jwy.exam;

public class Member {
  int id;
  String regDate;
  String updateDate;
  String loginId;
  String loginPw;
  String name;

  @Override
  public String toString() {
    return "Member{" +
        "id=" + id +
        ", regDate='" + regDate + '\'' +
        ", updateDate='" + updateDate + '\'' +
        ", loginId='" + loginId + '\'' +
        ", loginPw='" + loginPw + '\'' +
        ", name='" + name + '\'' +
        '}';
  }

  public Member(int id, String regDate, String updateDate, String loginId, String loginPw, String name) {
    this.id = id;
    this.regDate = regDate;
    this.updateDate = updateDate;
    this.loginId = loginId;
    this.loginPw = loginPw;
    this.name = name;
  }
}
