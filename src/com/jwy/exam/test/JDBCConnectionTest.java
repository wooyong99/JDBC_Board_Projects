package com.jwy.exam.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnectionTest {
  public static void main(String[] args) {
    Connection con = null;

    try {
      Class.forName("com.mysql.jdbc.Driver");

      String url = "jdbc:mysql://localhost:3306/text_board";

      con = DriverManager.getConnection(url, "jwy", "1234");
      System.out.println("연결 성공");
    } catch(ClassNotFoundException e) {
      System.out.println("드라이버 로딩 실패");
    }catch(SQLException e){
      System.out.println("에러: "+ e);
    }
    finally{
      try{
        if( con != null && !con.isClosed()){
          con.close();
        }
      }catch(SQLException e){
        e.printStackTrace();
      }
    }
  }
}
