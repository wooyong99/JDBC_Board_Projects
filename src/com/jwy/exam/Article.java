package com.jwy.exam;

public class Article {
  int id;
  String regDate;
  String updateDate;
  String title;
  String body;

  public Article(int id, String regDate, String updateDate, String title, String body) {
    this.id = id;
    this.regDate = regDate;
    this.updateDate = updateDate;
    this.title = title;
    this.body = body;
  }

  public Article(int id, String title, String body) {
    this.id = id;
    this.title = title;
    this.body = body;
  }

  @Override
  public String toString() {
    return String.format("{id = %d, regDate = %s, updateDate = %s, title = \"%s\", body = \"%s\"}", this.id, this.regDate, this.updateDate, this.title, this.body);
  }
}
