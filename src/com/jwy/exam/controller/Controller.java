package com.jwy.exam.controller;

import java.sql.Connection;
import java.util.Scanner;

public abstract class Controller {
  protected Connection con;
  protected Scanner sc;
  public Controller(Connection con, Scanner sc){
    this.con = con;
    this.sc = sc;
  }
}
