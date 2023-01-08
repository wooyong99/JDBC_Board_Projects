package com.jwy.exam.controller;

import com.jwy.exam.Container;

import java.sql.Connection;
import java.util.Scanner;

public abstract class Controller {
  protected Scanner sc;
  public Controller(){
    this.sc = Container.scanner;
  }
}
