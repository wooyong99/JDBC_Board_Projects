package com.jwy.exam;

import java.util.Map;

public class Article {
  public int id;
  public String regDate;
  public String updateDate;

  public int memberId;
  public String title;
  public String body;
  public String extra__writerName;

  public Article(int id, String regDate, String updateDate, int memberId, String title, String body) {
    this.id = id;
    this.regDate = regDate;
    this.updateDate = updateDate;
    this.memberId = memberId;
    this.title = title;
    this.body = body;
  }

  public Article(Map<String, Object> articleMap) {
    this.id = (int) articleMap.get("id");
    this.regDate = (String) articleMap.get("regDate");
    this.updateDate = (String) articleMap.get("updateDate");
    this.memberId = (int) articleMap.get("memberId");
    this.title = (String) articleMap.get("title");
    this.body = (String) articleMap.get("body");
    if (articleMap.get("extra__writerName") != null) {
      this.extra__writerName = (String) articleMap.get("extra__writerName");
    }
  }

  @Override
  public String toString() {
    return "Article{" +
        "id=" + id +
        ", regDate='" + regDate + '\'' +
        ", updateDate='" + updateDate + '\'' +
        ", memberId=" + memberId +
        ", title='" + title + '\'' +
        ", body='" + body + '\'' +
        ", extra__writerName='" + extra__writerName + '\'' +
        '}';
  }
}
