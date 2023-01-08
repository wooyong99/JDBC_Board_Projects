package com.jwy.exam.service;

import com.jwy.exam.Article;
import com.jwy.exam.Container;
import com.jwy.exam.dao.ArticleDao;

import java.util.List;

public class ArticleService {
  protected ArticleDao articleDao;
  public ArticleService(){
    this.articleDao = Container.articleDao;
  }

  public List<Article> getArticles() {
    return articleDao.getArticles();
  }

  public int add(int memberId, String title, String body) {
    return articleDao.add(memberId, title, body);
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
