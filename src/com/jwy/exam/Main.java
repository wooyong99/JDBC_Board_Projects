package com.jwy.exam;

import com.jwy.exam.exception.SQLErrorException;

public class Main {
  public static void main(String[] args) {
    try{
      App app = new App();
      app.run();
    }catch(SQLErrorException e){
      System.out.println(e.getMessage());
      e.getOrigin().printStackTrace();
    }

  }
}