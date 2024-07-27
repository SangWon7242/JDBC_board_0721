import com.sbs.java.board.article.Article;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCUpdateTest {
  public static void main(String[] args) {
    // 데이터베이스 연결 정보
    // 데이터베이스 URL, 사용자 이름, 비밀번호 설정
    // url : 목적지
    String url = "jdbc:mysql://127.0.0.1:3306/JDBC_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
    String user = "sbsst"; // 계정이 root면 "root" 입력
    String password = "sbs123414"; // 비밀번호가 없다면 ""(공백) 입력

    Connection conn = null;
    PreparedStatement pstat = null;

    try {
      // MySQL JDBC 드라이버 로드
      Class.forName("com.mysql.cj.jdbc.Driver");

      // 데이터베이스 연결
      conn = DriverManager.getConnection(url, user, password);

      int id = 2;
      String subject = "제목 수정";
      String content = "내용 수정";

      String sql = "UPDATE article";
      sql += " SET updateDate = NOW()";
      sql += ", `subject` = '%s'".formatted(subject);
      sql += ", content = '%s'".formatted(content);
      sql += " WHERE id = %d;".formatted(id);

      System.out.println("sql : " + sql);

      pstat = conn.prepareStatement(sql);

      pstat.executeUpdate(sql);

      // 연결이 성공했음을 출력
      System.out.println("데이터베이스 연결 성공!");

    } catch (ClassNotFoundException e) {
      System.out.println("JDBC 드라이버를 찾을 수 없습니다.");
      e.printStackTrace();
    } catch (SQLException e) {
      System.out.println("데이터베이스 연결 실패!");
      e.printStackTrace();
    } finally {
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
