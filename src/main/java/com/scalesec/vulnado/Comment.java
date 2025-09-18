package com.scalesec.vulnado;
import org.apache.catalina.Server;

import java.sql.*;
import org.apache.catalina.Server;
import java.util.Date;
import java.sql.*;
import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import java.util.UUID;

public class Comment {
    private String id, username, body;
    private Timestamp createdOn;

    public Comment(String id, String username, String body, Timestamp createdOn) {
        this.id = id;
        this.username = username;
        this.body = body;
        this.createdOn = createdOn;
    }
  }

    public static Comment create(String username, String body) {
        long time = new Date().getTime();
        Timestamp timestamp = new Timestamp(time);
        Comment comment = new Comment(UUID.randomUUID().toString(), username, body, timestamp);
        try {
            if (comment.commit()) {
                return comment;
            } else {
                throw new BadRequest("Unable to save comment");
            }
        } catch (Exception e) {
            throw new ServerError(e.getMessage());
        }
  }

    public static List<Comment> fetchAll() {
        List<Comment> comments = new ArrayList<>();
        try (Connection cxn = Postgres.connection();
             Statement stmt = cxn.createStatement()) {
    try {
      Connection cxn = Postgres.connection();
      stmt = cxn.createStatement();

            String query = "SELECT * FROM comments";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String id = rs.getString("id");
                String username = rs.getString("username");
                String body = rs.getString("body");
                Timestamp createdOn = rs.getTimestamp("createdon");
                Comment c = new Comment(id, username, body, createdOn);
                comments.add(c);
      }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return comments;
    }
  }

    public static Boolean delete(String id) {
        String sql = "DELETE FROM comments WHERE id = ?";
        try (Connection con = Postgres.connection();
             PreparedStatement pStatement = con.prepareStatement(sql)) {
      Connection con = Postgres.connection();
      PreparedStatement pStatement = con.prepareStatement(sql);
            pStatement.setString(1, id);
            return pStatement.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
  }

    private Boolean commit() throws SQLException {
        String sql = "INSERT INTO comments (id, username, body, createdon) VALUES (?, ?, ?, ?)";
        try (Connection con = Postgres.connection();
             PreparedStatement pStatement = con.prepareStatement(sql)) {
    PreparedStatement pStatement = con.prepareStatement(sql);
            pStatement.setString(1, this.id);
            pStatement.setString(2, this.username);
            pStatement.setString(3, this.body);
            pStatement.setTimestamp(4, this.createdOn);
            return pStatement.executeUpdate() == 1;
        }
}
