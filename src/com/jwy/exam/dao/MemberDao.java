package com.jwy.exam.dao;

import com.jwy.exam.Member;
import com.jwy.exam.util.DBUtil;
import com.jwy.exam.util.SecSql;

import java.sql.Connection;
import java.util.Map;

public class MemberDao {
  protected Connection con;

  public MemberDao(Connection con){
    this.con = con;
  }

  public boolean isLogined(String loginId) {
    SecSql sql = new SecSql();
    sql.append("SELECT COUNT(*) > 0");
    sql.append("FROM `member`");
    sql.append("WHERE loginId= ?", loginId);

    return DBUtil.selectRowBooleanValue(con, sql);
  }

  public int join(String loginId, String loginPw, String name) {
    SecSql sql = new SecSql();
    sql.append("INSERT INTO `member`");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", loginId = ?", loginId);
    sql.append(", loginPw = ?", loginPw);
    sql.append(", `name` = ?", name);

    int id = DBUtil.insert(con, sql);

    return id;
  }

  public Member getMemberByLoginId(String loginId) {
    SecSql sql = new SecSql();
    sql.append("SELECT * FROM `member`");
    sql.append("WHERE loginId = ?",loginId);

    Map<String, Object> memberMap = DBUtil.selectRow(con, sql);
    if(memberMap.isEmpty()){
      return null;
    }else{
      return new Member(memberMap);
    }
  }
}
