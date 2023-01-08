package com.jwy.exam.session;

import com.jwy.exam.Member;

public class Session {
  public int loginedMemberId;
  public Member loginedMember;
  public Session(){
    loginedMemberId = -1;
  }

  public boolean isLogined() {
    return loginedMemberId != -1;
  }

  public void login(Member member) {
    loginedMember = member;
    loginedMemberId = member.id;
  }

  public void logout() {
    loginedMember = null;
    loginedMemberId = -1;
  }
}
