package com.jwy.exam.controller;

import com.jwy.exam.Container;
import com.jwy.exam.Member;
import com.jwy.exam.service.MemberService;

public class MemberController extends Controller {
  MemberService memberService;

  public MemberController() {
    this.memberService = Container.memberService;
  }

  public void join() {
    System.out.println("== 회원 가입 ==");
    String loginId, loginPw, loginPwConfirm, name;
    while (true) {
      System.out.printf("로그인 아이디 : ");
      loginId = sc.nextLine().trim();
      if (loginId.length() == 0) {
        System.out.println("로그인 아이디를 입력해주세요.");
        continue;
      }

      boolean loginedDu = memberService.isLogined(loginId);

      if (loginedDu) {
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

    int id = memberService.join(loginId, loginPw, name);

    System.out.printf("%d번 (%s) 회원이 생성되었습니다.\n", id, name);
  }

  public void login() {
    if(Container.session.isLogined()){
      System.out.println("이미 로그인 상태입니다.");
      return;
    }
    String loginId;
    String loginPw;
    Member member;
    System.out.println("== 로그인 ==");
    while(true){
      System.out.printf("아이디 입력 : ");
      loginId = sc.nextLine().trim();
      if(loginId.length() == 0){
        System.out.println("아이디를 입력해주세요.");
        continue;
      }
      boolean loginIdDu = memberService.isLogined(loginId);
      if(!loginIdDu){
        System.out.printf("%s(은)는 존재하지 않는 아이디입니다.\n",loginId);
        continue;
      }
      break;
    }
    member = memberService.getMemberByLoginId(loginId);
    int tryCount = 0;
    int tryMaxCount=3;
    while(true){
      if(tryCount >= tryMaxCount){
        System.out.println("비밀번호 확인 후 다시 입력해주세요.");
        break;
      }
      System.out.printf("비밀번호 입력 : ");
      loginPw = sc.nextLine().trim();
      if(loginPw.length() == 0){
        System.out.println("비밀번호를 입력해주세요.");
        continue;
      }
      if(!loginPw.equals(member.loginPw)){
        System.out.println("비밀번호가 일치하지 않습니다.");
        tryCount++;
        continue;
      }
      System.out.printf("%s님 환영합니다.\n", member.name);
      Container.session.login(member);
      break;
    }
  }

  public void whoami() {
    if(Container.session.isLogined()){
      System.out.println(Container.session.loginedMember.loginId);
    }else{
      System.out.println("로그아웃 상태입니다.");
    }
  }

  public void logout() {
    if(Container.session.isLogined()){
      Container.session.logout();
      System.out.println("로그아웃 되었습니다.");
    }else{
      System.out.println("이미 로그아웃 상태입니다.");
    }
  }
}
