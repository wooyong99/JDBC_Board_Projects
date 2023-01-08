package com.jwy.exam.service;

import com.jwy.exam.Container;
import com.jwy.exam.Member;
import com.jwy.exam.dao.MemberDao;

import java.sql.Connection;

public class MemberService {
  protected Connection con;
  MemberDao memberDao;

  public MemberService(){
    this.memberDao = Container.memberDao;
  }

  public boolean isLogined(String loginId) {
    return memberDao.isLogined(loginId);
  }

  public int join(String loginId, String loginPw, String name) {
    return memberDao.join(loginId, loginPw, name);
  }

  public Member getMemberByLoginId(String loginId) {
    return memberDao.getMemberByLoginId(loginId);
  }
}
