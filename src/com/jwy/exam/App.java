package com.jwy.exam;

import com.jwy.exam.controller.ArticleController;
import com.jwy.exam.controller.MemberController;
import com.jwy.exam.util.DBUtil;
import com.jwy.exam.util.SecSql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class App {
  public void run() {
    Scanner sc = Container.scanner;
    String cmd;
    while (true) {
      System.out.printf("명령어) ");
      cmd = sc.nextLine();
      Connection con = null;
      try {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/text_board";
        con = DriverManager.getConnection(url, "jwy", "1234");
        int actionResult = doAction(con, sc, cmd);
        if (actionResult == -1) {
          break;
        }
      } catch (ClassNotFoundException e) {
        System.out.println("예외 : MySQL 연결을 할 수 없습니다.");
        System.out.println("프로그램을 종료합니다.");
        break;
      } catch (SQLException e) {
        System.out.println("예외 : DB에 연결할 수 없습니다.");
        System.out.println("프로그램을 종료합니다.");
        break;
      } finally {
        try {
          if (con != null && !con.isClosed()) {
            con.close();
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private int doAction(Connection con, Scanner sc, String cmd) {
    ArticleController articleController = new ArticleController(con, sc);
    MemberController memberController = new MemberController(con, sc);
    if (cmd.equals("article list")) {
      articleController.showList();
    } else if (cmd.equals("article write")) {
      articleController.add();
    } else if (cmd.startsWith("article detail ")) {
      articleController.showDetail(cmd);
    } else if (cmd.startsWith("article modify ")) {
      articleController.modify(cmd);
    } else if (cmd.startsWith("article delete ")) {
      articleController.delete(cmd);
    } else if (cmd.equals("member join")) {
      memberController.join();
    } else if (cmd.equals("system exit")) {
      System.out.println("== 시스템 종료 ==");
      System.exit(0);
    } else {
      System.out.println("명령어를 확인해주세요.");
    }
    return 0;
  }
}
