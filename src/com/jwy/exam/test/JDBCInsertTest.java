package com.jwy.exam.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCInsertTest {
  public static void main(String[] args){
    Connection con = null;
    PreparedStatement psts = null;
    try{
      Class.forName("com.mysql.jdbc.Driver");
      String url = "jdbc:mysql://localhost:3306/text_board";
      con = DriverManager.getConnection(url, "jwy","1234");
      System.out.println("연결 성공 !");

      String sql = "INSERT INTO article";
      sql += " SET regDate = NOW()";
      sql += ", updateDate = NOW()";
      sql += ", title = CONCAT(\"제목\",RAND())";
      sql += ", `body` = CONCAT(\"내용\",RAND());";

      psts = con.prepareStatement(sql);
      int affectRows = psts.executeUpdate();

      System.out.println("affectRows : " + affectRows);
    }catch(ClassNotFoundException e){
      System.out.println("연결 실패 !");
    }catch(SQLException e){
      System.out.println("에러 : " + e);
    }
    finally {
      try{
        if(con != null && !con.isClosed()){
          con.close();
        }
      }catch(SQLException e){
        e.printStackTrace();
      }
      try{
        if(psts != null && !psts.isClosed()){
          psts.close();
        }
      }catch(SQLException e){
        e.printStackTrace();
      }
    }
  }
}
