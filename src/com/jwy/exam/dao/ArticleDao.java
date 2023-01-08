package com.jwy.exam.dao;

import com.jwy.exam.dto.Article;
import com.jwy.exam.Container;
import com.jwy.exam.util.DBUtil;
import com.jwy.exam.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleDao {
  public List<Article> getArticles() {
    List<Article> articles = new ArrayList<Article>();
    SecSql sql = new SecSql();
    sql.append("SELECT article.*, member.name AS extra__writerName ");
    sql.append("FROM article");
    sql.append("INNER JOIN member");
    sql.append("ON article.memberId = member.id");
    sql.append("ORDER BY article.id DESC");

    List<Map<String, Object>> articleListMap = DBUtil.selectRows(Container.con, sql);

    for (Map<String, Object> articleMap : articleListMap) {
      articles.add(new Article(articleMap));
    }
    return articles;
  }

  public int add(int memberId, String title, String body) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", memberId = ?", memberId);
    sql.append(", title = ?", title);
    sql.append(", body = ?", body);

    int id = DBUtil.insert(Container.con, sql);
    return id;
  }

  public Article getArticleById(int id) {
    SecSql sql = new SecSql();
    sql.append("SELECT * FROM article");
    sql.append("WHERE id = ?", id);

    Map<String, Object> articleMap = DBUtil.selectRow(Container.con, sql);

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

    return DBUtil.selectRowIntValue(Container.con, sql);

  }

  public void update(String title, String body, int id) {
    SecSql sql = new SecSql();
    sql.append("UPDATE article");
    sql.append("SET updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", `body` = ?", body);
    sql.append("WHERE id = ?", id);

    DBUtil.update(Container.con, sql);
  }

  public void delete(int id) {
    SecSql sql = new SecSql();
    sql.append("DELETE FROM article");
    sql.append("WHERE id = ?", id);

    DBUtil.delete(Container.con, sql);
  }
}
