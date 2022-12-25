package com.jwy.exam;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {
  public void run() {
    int articleIdx = 0;
    Scanner sc = Container.scanner;
    String cmd;
    while (true) {
      System.out.printf("명령어) ");
      cmd = sc.nextLine();
      if (cmd.equals("article list")) {
        Connection con = null;
        PreparedStatement psts = null;
        ResultSet rs = null;
        List<Article> articles = new ArrayList<Article>();
        try {
          Class.forName("com.mysql.jdbc.Driver");
          String url = "jdbc:mysql://localhost:3306/text_board";
          con = DriverManager.getConnection(url, "jwy", "1234");

          String sql = "SELECT *";
          sql += " FROM article";
          sql += " ORDER BY id DESC;";

          psts = con.prepareStatement(sql);
          rs = psts.executeQuery();

          while (rs.next()) {
            int id = rs.getInt("id");
            String regDate = rs.getString("regDate");
            String updateDate = rs.getString("updateDate");
            String title = rs.getString("title");
            String body = rs.getString("body");

            Article article = new Article(id, regDate, updateDate, title, body);
            articles.add(article);
          }
          //System.out.println("결과 : " + articles);
        } catch (ClassNotFoundException e) {
          System.out.println("연결 실패 !");
        } catch (SQLException e) {
          System.out.println("에러 : " + e);
        } finally {
          try {
            if (rs != null && !rs.isClosed()) {
              rs.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
          try {
            if (psts != null && !psts.isClosed()) {
              psts.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
          try {
            if (con != null && !con.isClosed()) {
              con.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
        if (articles.isEmpty()) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }
        System.out.println("== 게시물 리스트 ==");
        System.out.println("번호 / 제목");
        for (Article article : articles) {
          System.out.printf("%d / %s\n", article.id, article.title);
        }
      } else if (cmd.equals("article write")) {
        System.out.println("== 게시물 작성 ==");
        int id = ++articleIdx;
        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();


        Connection con = null;
        PreparedStatement psts = null;
        try {
          Class.forName("com.mysql.jdbc.Driver");
          String url = "jdbc:mysql://localhost:3306/text_board";
          con = DriverManager.getConnection(url, "jwy", "1234");

          String sql = "INSERT INTO article";
          sql += " SET regDate = NOW()";
          sql += ", updateDate = NOW()";
          sql += ", title = \"" + title + "\"";
          sql += ", `body` = \"" + body + "\";";

          psts = con.prepareStatement(sql);
          psts.executeUpdate();

        } catch (ClassNotFoundException e) {
          System.out.println("연결 실패 !");
        } catch (SQLException e) {
          System.out.println("에러 : " + e);
        } finally {
          try {
            if (con != null && !con.isClosed()) {
              con.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
          try {
            if (psts != null && !psts.isClosed()) {
              psts.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }

        Article article = new Article(id, title, body);
        System.out.printf("작성된 게시물 : %s\n", article.toString());
        System.out.printf("%d번 게시물이 작성되었습니다.\n", id);

      } else if (cmd.startsWith("article modify ")) {
        String id = cmd.split(" ")[2];
        Connection con = null;
        PreparedStatement psts = null;
        ResultSet rs = null;
        String url = "jdbc:mysql://localhost:3306/text_board";
        try {
          Class.forName("com.mysql.jdbc.Driver");
          con = DriverManager.getConnection(url, "jwy", "1234");
          String sql = "select group_concat(id) as c from article";
          psts = con.prepareStatement(sql);
          rs = psts.executeQuery();
          if(rs.next()){
            List<String> idx_arr = new ArrayList<>(Arrays.asList(rs.getString("c").split(",")));
            if(idx_arr.contains(id)) {
              System.out.printf("== %s번 게시물 수정 ==\n", id);
              System.out.printf("새 제목 : ");
              String title = sc.nextLine();
              System.out.printf("새 내용 : ");
              String body = sc.nextLine();

              sql = "UPDATE article";
              sql += " SET updateDate = NOW()";
              sql += ", title = \"" + title + "\"";
              sql += ", `body` = \"" + body + "\"";
              sql += " WHERE id =" + id;

              psts = con.prepareStatement(sql);
              psts.executeUpdate();
            }else{
                System.out.println("수정안됨");
                continue;
            }
          }
        } catch (ClassNotFoundException e) {
          System.out.println("연결 실패");
        } catch (SQLException e) {
          e.printStackTrace();
        } finally {
          try {
            if (psts != null && !psts.isClosed()) {
              psts.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
          try {
            if (con != null && !con.isClosed()) {
              con.close();
            }
          } catch (SQLException e) {
            e.printStackTrace();
          }
        }
        System.out.printf("%s번 게시물이 수정되었습니다.\n", id);
      } else if (cmd.equals("system exit")) {
        System.out.println("== 시스템 종료 ==");
        break;
      } else {
        System.out.println("명령어를 확인해주세요.");
      }
    }


  }
}
