package com.example.springbasic.DB_1.repository;

import com.example.springbasic.DB_1.connection.DBConnectionUtil;
import com.example.springbasic.DB_1.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

@Slf4j
public class MemberRepositoryV0 {

    public Member save(Member member) throws SQLException {
        // Sql Injection 공격 예방을 위해 바인딩을 사용
        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection con = null;
        PreparedStatement preparedStatement = null;

        try {
            con = getConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, member.getMemberId());
            preparedStatement.setInt(2, member.getMoney());
            preparedStatement.executeUpdate();
            return member;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        } finally {
            preparedStatement.close();
            con.close();
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId=" +
                        memberId);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            close(con, pstmt, rs);
        }
    }


    private void close(Connection con, Statement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (Exception e) {
                log.info("e", e);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (Exception e) {
                log.info("e", e);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                log.info("e", e);
            }
        }
    }

    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }

}
