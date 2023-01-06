package com.jwy.exam.util;

import java.sql.Connection;

public class Util {
  // loginId 중복 검증 메소드
  public static boolean loginIdValidation(Connection con, String loginId) {
    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) AS cnt");
    sql.append("FROM `member`");
    sql.append("WHERE loginId = ?", loginId);

    int articleCount = DBUtil.selectRowIntValue(con, sql);
    if (articleCount == 0) {
      return true;
    } else {
      return false;
    }
  }
}
