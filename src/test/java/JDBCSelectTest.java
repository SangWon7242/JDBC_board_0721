import com.sbs.java.board.article.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCSelectTest {
  public static void main(String[] args) {
    // 데이터베이스 연결 정보
    // 데이터베이스 URL, 사용자 이름, 비밀번호 설정
    // url : 목적지
    String url = "jdbc:mysql://127.0.0.1:3306/JDBC_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
    String user = "sbsst"; // 계정이 root면 "root" 입력
    String password = "sbs123414"; // 비밀번호가 없다면 ""(공백) 입력

    Connection conn = null;
    PreparedStatement pstat = null;
    ResultSet rs = null;

    List<Article> articles = new ArrayList<>();

    try {
      // MySQL JDBC 드라이버 로드
      Class.forName("com.mysql.cj.jdbc.Driver");

      // 데이터베이스 연결
      conn = DriverManager.getConnection(url, user, password);

      String sql = "SELECT *";
      sql += " FROM article";
      sql += " ORDER BY id DESC;";

      System.out.println("sql : " + sql);

      pstat = conn.prepareStatement(sql);

      // SQL 쿼리 실행
      rs = pstat.executeQuery(sql);

      while (rs.next()) { // rs.next() : 페이지가 있으면 true, 없으면 false
        int id = rs.getInt("id");
        String regDate = rs.getString("regDate");
        String updateDate = rs.getString("updateDate");
        String subject = rs.getString("subject");
        String content = rs.getString("content");

        Article article = new Article(id, subject, content);
        articles.add(article);
      }

      System.out.println(articles);

      // 연결이 성공했음을 출력
      System.out.println("데이터베이스 연결 성공!");

    } catch (ClassNotFoundException e) {
      System.out.println("JDBC 드라이버를 찾을 수 없습니다.");
      e.printStackTrace();
    } catch (SQLException e) {
      System.out.println("데이터베이스 연결 실패!");
      e.printStackTrace();
    } finally {
      // 자원 정리
      try {
        if (rs != null && !rs.isClosed()) {
          rs.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      try {
        if (pstat != null && !pstat.isClosed()) {
          pstat.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
      try {
        if (conn != null && !conn.isClosed()) {
          conn.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
