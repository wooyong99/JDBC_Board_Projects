package com.jwy.exam.controller;

import com.jwy.exam.util.DBUtil;
import com.jwy.exam.util.SecSql;
import com.jwy.exam.util.Util;

import java.sql.Connection;
import java.util.Scanner;

public class MemberController {
  private Scanner sc;
  private Connection con;

  public void setScanner(Scanner sc) {
    this.sc = sc;
  }

  public void setCon(Connection con) {
    this.con = con;
  }
  public void join(String cmd){
    System.out.println("== 회원 가입 ==");
    String loginId, loginPw, loginPwConfirm, name;
    while (true) {
      System.out.printf("로그인 아이디 : ");
      loginId = sc.nextLine().trim();
      if (loginId.length() == 0) {
        System.out.println("로그인 아이디를 입력해주세요.");
        continue;
      }
      if (! Util.loginIdValidation(con, loginId)) {
        System.out.println("로그인 아이디가 중복됩니다.");
        continue;
      }
      break;
    }
    while (true) {
      System.out.printf("로그인 비밀번호 : ");
      loginPw = sc.nextLine().trim();
      if (loginPw.length() == 0) {
        System.out.println("로그인 비밀번호를 입력해주세요.");
        continue;
      }
      while (true) {
        System.out.printf("로그인 비밀번호 확인 : ");
        loginPwConfirm = sc.nextLine().trim();
        if (loginPwConfirm.length() == 0) {
          System.out.println("로그인 비밀번호 확인을 입력해주세요.");
          continue;
        }
        if (!loginPwConfirm.equals(loginPw)) {
          System.out.println("로그인 비밀번호가 일치하지 않습니다.");
          continue;
        }
        break;
      }
      break;
    }
    while (true) {
      System.out.printf("이름 : ");
      name = sc.nextLine().trim();
      if (name.length() == 0) {
        System.out.println("이름을 입력해주세요.");
        continue;
      }
      break;
    }
    SecSql sql = new SecSql();
    sql.append("INSERT INTO `member`");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", loginId = ?", loginId);
    sql.append(", loginPw = ?", loginPw);
    sql.append(", `name` = ?", name);

    int id = DBUtil.insert(con, sql);
    System.out.printf("%d번 (%s) 회원이 생성되었습니다.\n", id, name);
  }
}
