package com.jwy.exam;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
  public void run() {
    int articleIdx = 0;
    Scanner sc = Container.scanner;
    String cmd;
    ArrayList<Article> articles = new ArrayList<Article>();
    while (true) {
      System.out.printf("명령어) ");
      cmd = sc.nextLine();
      if (cmd.equals("article list")) {
        if (articles.isEmpty()) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }
        System.out.println("== 게시물 리스트 ==");
        System.out.println("번호 / 제목");
        for (Article article : articles) {
          System.out.printf("%d / %s\n", article.id, article.title);
        }
      } else if (cmd.equals("article write")) {
        System.out.println("== 게시물 작성 ==");
        int id = ++articleIdx;
        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();
        Article article = new Article(id, title, body);
        articles.add(article);
        System.out.printf("작성된 게시물 - %s\n", article.toString());
        System.out.printf("%d번 게시물이 작성되었습니다.\n", id);
      } else if (cmd.equals("system exit")) {
        System.out.println("== 시스템 종료 ==");
        break;
      } else {
        System.out.println("명령어를 확인해주세요.");
      }
    }


  }
}
