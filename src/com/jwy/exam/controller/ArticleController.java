package com.jwy.exam.controller;

import com.jwy.exam.Article;
import com.jwy.exam.Container;
import com.jwy.exam.service.ArticleService;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class ArticleController extends Controller{
  ArticleService articleService;
  public ArticleController(){
    this.articleService = Container.articleService;
  }

  public void showList() {
    List<Article> articles = articleService.getArticles();

    if (articles.isEmpty()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }
    System.out.println("== 게시물 리스트 ==");
    System.out.println("번호 / 제목");
    for (Article article : articles) {
      System.out.printf("%d / %s\n", article.id, article.title);
    }
  }

  public void add() {
    System.out.println("== 게시물 작성 ==");
    System.out.printf("제목 : ");
    String title = sc.nextLine();
    System.out.printf("내용 : ");
    String body = sc.nextLine();

    int id = articleService.add(title, body);

    System.out.printf("%d번 게시글이 작성되었습니다.\n", id);
  }

  public void showDetail(String cmd) {
    int id = Integer.parseInt(cmd.split(" ")[2]);

    Article article = articleService.getArticleById(id);

    if (article == null) {
      System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
      return;
    }
    System.out.printf("== %d번 게시글 상세보기 ==\n", id);
    System.out.printf("생성날짜 : %s\n", article.regDate);
    System.out.printf("수정날짜 : %s\n", article.updateDate);
    System.out.printf("제목 : %s\n", article.title);
    System.out.printf("내용 : %s\n", article.body);
  }

  public void modify(String cmd) {
    int id = Integer.parseInt(cmd.split(" ")[2]);
    int articleCount = articleService.exists(id);

    if (articleCount == 0) {
      System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
      return;
    }
    System.out.printf("== %d번 게시물 수정 ==\n", id);
    System.out.printf("새 제목 : ");
    String title = sc.nextLine();
    System.out.printf("새 내용 : ");
    String body = sc.nextLine();

    articleService.update(title, body, id);

    System.out.printf("%d번 게시물이 수정되었습니다.\n", id);
  }

  public void delete(String cmd) {
    int id = Integer.parseInt(cmd.split(" ")[2]);

    int articleCount = articleService.exists(id);
//    SecSql sql = new SecSql();
//    sql.append("SELECT COUNT(*) AS cnt");
//    sql.append("FROM article");
//    sql.append("WHERE id = ?", id);
//
//    int articleCount = DBUtil.selectRowIntValue(con, sql);
    if (articleCount == 0) {
      System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
      return;
    }

    articleService.delete(id);
    System.out.printf("%d번 게시글이 삭제되었습니다.\n", id);
  }
}
