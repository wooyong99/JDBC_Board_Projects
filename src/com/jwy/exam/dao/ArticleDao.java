package com.jwy.exam.dao;

import com.jwy.exam.Article;
import com.jwy.exam.util.DBUtil;
import com.jwy.exam.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleDao {
  protected Connection con;

  public ArticleDao(Connection con){
    this.con = con;
  }

  public List<Article> getArticles() {
    List<Article> articles = new ArrayList<Article>();
    SecSql sql = new SecSql();
    sql.append("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");

    List<Map<String, Object>> articleListMap = DBUtil.selectRows(con, sql);

    for (Map<String, Object> articleMap : articleListMap) {
      articles.add(new Article(articleMap));
    }
    return articles;
  }

  public int add(String title, String body) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", body = ?", body);

    int id = DBUtil.insert(con, sql);
    return id;
  }

  public Article getArticleById(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT * FROM article");
    sql.append("WHERE id = ?", id);

    Map<String, Object> articleMap = DBUtil.selectRow(con, sql);

    if(articleMap.isEmpty()){
      return null;
    }else{
      return new Article(articleMap);
    }
  }

  public int exists(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) AS cnt");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    return DBUtil.selectRowIntValue(con, sql);

  }

  public void update(String title, String body, int id) {
    SecSql sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("SET updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", `body` = ?", body);
    sql.append("WHERE id = ?", id);

    DBUtil.update(con, sql);
  }

  public void delete(int id) {
    SecSql sql = new SecSql();
    sql.append("DELETE FROM article");
    sql.append("WHERE id = ?", id);

    DBUtil.delete(con, sql);
  }
}
