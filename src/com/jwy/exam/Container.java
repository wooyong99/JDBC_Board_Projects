package com.jwy.exam;

import com.jwy.exam.controller.ArticleController;
import com.jwy.exam.controller.MemberController;
import com.jwy.exam.dao.ArticleDao;
import com.jwy.exam.dao.MemberDao;
import com.jwy.exam.service.ArticleService;
import com.jwy.exam.service.MemberService;

import java.sql.Connection;
import java.util.Scanner;

public class Container {
  public static Scanner scanner;
  public static Connection con;
  public static ArticleController articleController;
  public static MemberController memberController;

  public static ArticleService articleService;
  public static MemberService memberService;

  public static ArticleDao articleDao;
  public static MemberDao memberDao;
  public static void init(){
    scanner = new Scanner(System.in);

    articleDao = new ArticleDao();
    memberDao = new MemberDao();

    articleService = new ArticleService();
    memberService = new MemberService();

    articleController = new ArticleController();
    memberController = new MemberController();
  }
}
