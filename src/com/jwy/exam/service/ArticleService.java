package com.jwy.exam.service;

import com.jwy.exam.Article;
import com.jwy.exam.dao.ArticleDao;

import java.sql.Connection;
import java.util.List;

public class ArticleService {
  protected ArticleDao articleDao;
  protected Connection con;

  public ArticleService(Connection con){
    this.con = con;
    this.articleDao = new ArticleDao(con);
  }

  public List<Article> getArticles() {
    return articleDao.getArticles();
  }

  public int add(String title, String body) {
    return articleDao.add(title, body);
  }

  public Article getArticleById(int id) {
    return articleDao.getArticleById(id);
  }

  public int exists(int id) {
    return articleDao.exists(id);
  }

  public void update(String title, String body, int id) {
    articleDao.update(title, body, id);
  }

  public void delete(int id) {
    articleDao.delete(id);
  }
}
