package com.jwy.exam;

import com.jwy.exam.util.DBUtil;
import com.jwy.exam.util.SecSql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    if (cmd.equals("article list")) {
      List<Article> articles = new ArrayList<Article>();

      SecSql sql = new SecSql();
      sql.append("SELECT *");
      sql.append("FROM article");
      sql.append("ORDER BY id DESC");

      List<Map<String, Object>> articleListMap = DBUtil.selectRows(con, sql);

      for (Map<String, Object> articleMap : articleListMap) {
        articles.add(new Article(articleMap));
      }
      if (articles.isEmpty()) {
        System.out.println("게시물이 존재하지 않습니다.");
        return 0;
      }
      System.out.println("== 게시물 리스트 ==");
      System.out.println("번호 / 제목");
      for (Article article : articles) {
        System.out.printf("%d / %s\n", article.id, article.title);
      }
    } else if (cmd.equals("article write")) {
      System.out.println("== 게시물 작성 ==");
      System.out.printf("제목 : ");
      String title = sc.nextLine();
      System.out.printf("내용 : ");
      String body = sc.nextLine();

      SecSql sql = new SecSql();
      sql.append("INSERT INTO article");
      sql.append("SET regDate = NOW()");
      sql.append(", updateDate = NOW()");
      sql.append(", title = ?", title);
      sql.append(", body = ?", body);

      int id = DBUtil.insert(con, sql);

      System.out.printf("%d번 게시글이 작성되었습니다.\n", id);
    } else if (cmd.startsWith("article detail ")) {
      Article article;
      int id = Integer.parseInt(cmd.split(" ")[2]);
      SecSql sql = new SecSql();
      sql.append("SELECT * FROM article");
      sql.append("WHERE id = ?", id);

      Map<String, Object> articleMap = DBUtil.selectRow(con, sql);
      if (articleMap.isEmpty()) {
        System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
        return 0;
      } else {
        article = new Article(articleMap);
      }
      System.out.println("== %d번 게시글 상세보기 ==");
      System.out.printf("생성날짜 : %s\n", article.regDate);
      System.out.printf("수정날짜 : %s\n", article.updateDate);
      System.out.printf("제목 : %s\n", article.title);
      System.out.printf("내용 : %s\n", article.body);
    } else if (cmd.startsWith("article modify ")) {
      int id = Integer.parseInt(cmd.split(" ")[2]);
      SecSql sql = new SecSql();
      sql.append("SELECT COUNT(*) AS cnt");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      int articleCount = DBUtil.selectRowIntValue(con, sql);

      if (articleCount == 0) {
        System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
        return 0;
      }
      System.out.printf("== %d번 게시물 수정 ==\n", id);
      System.out.printf("새 제목 : ");
      String title = sc.nextLine();
      System.out.printf("새 내용 : ");
      String body = sc.nextLine();
      sql = new SecSql();
      sql.append("UPDATE article");
      sql.append("SET updateDate = NOW()");
      sql.append(", title = ?", title);
      sql.append(", `body` = ?", body);
      sql.append("WHERE id = ?", id);

      DBUtil.update(con, sql);
      System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
    } else if (cmd.startsWith("article delete ")) {
      int id = Integer.parseInt(cmd.split(" ")[2]);
      SecSql sql = new SecSql();
      sql.append("SELECT COUNT(*) AS cnt");
      sql.append("FROM article");
      sql.append("WHERE id = ?", id);

      int articleCount = DBUtil.selectRowIntValue(con, sql);
      if (articleCount == 0) {
        System.out.printf("%d번 게시글은 존재하지 않습니다.", id);
        return 0;
      }
      sql = new SecSql();
      sql.append("DELETE FROM article");
      sql.append("WHERE id = ?", id);

      DBUtil.delete(con, sql);
    } else if (cmd.equals("member join")) {
      System.out.println("== 회원 가입 ==");
      String loginId, loginPw, loginPwConfirm, name;
      while (true) {
        System.out.printf("로그인 아이디 : ");
        loginId = sc.nextLine().trim();
        if (loginId.length() == 0) {
          System.out.println("로그인 아이디를 입력해주세요.");
          continue;
        }
        if (!loginIdValidation(con, loginId)) {
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
    } else if (cmd.equals("system exit")) {
      System.out.println("== 시스템 종료 ==");
      System.exit(0);
    } else {
      System.out.println("명령어를 확인해주세요.");
    }
    return 0;
  }

  // loginId 중복 검증 메소드
  private boolean loginIdValidation(Connection con, String loginId) {
    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) AS cnt");
    sql.append("FROM `member`");
    sql.append("WHERE loginId = ?", loginId);

    int articleCount = DBUtil.selectRowIntValue(con, sql);
    if (articleCount == 0) {
      return true;
    } else {
      return false;
    }
  }
}
