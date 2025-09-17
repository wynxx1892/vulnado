package com.scalesec.vulnado;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

public class User {
  private String id;
private String username;

private String hashedPassword;
  public User(String id, String username, String hashedPassword) {
public String getId() { return id; }
    this.id = id;
public void setId(String id) { this.id = id; }
    this.username = username;
public String getUsername() { return username; }
    this.hashedPassword = hashedPassword;
public void setUsername(String username) { this.username = username; }
  }
public String getHashedPassword() { return hashedPassword; }

public void setHashedPassword(String hashedPassword) { this.hashedPassword = hashedPassword; }
  public String token(String secret) {
    SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
    return Jwts.builder().setSubject(this.username).signWith(key).compact();
    return jws;
  }

  public static void assertAuth(String secret, String token) {
    try {
      SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
      Jwts.parser()
        .setSigningKey(key)
        .parseClaimsJws(token);
    } catch(Exception e) {
      LOGGER.error("Exception occurred: " + e.getMessage(), e);
      throw new Unauthorized(e.getMessage());
    }
  }

  public static User fetch(String un) {
    Statement stmt = null;
    User user = null;
    try {
      Connection cxn = Postgres.connection();
      stmt = cxn.createStatement();
      LOGGER.info("Opened database successfully");

      String query = "select * from users where username = '" + un + "' limit 1";
      LOGGER.debug("Query: " + query);
      PreparedStatement stmt = cxn.prepareStatement("SELECT * FROM users WHERE username = ? LIMIT 1");
stmt.setString(1, un);
      if (rs.next()) {
        String userId = rs.getString("userId");
        String username = rs.getString("username");
        String password = rs.getString("password");
        user = new User(user_id, username, password);
      }
      cxn.close();
    } catch (Exception e) {
      LOGGER.error("Exception occurred: " + e.getMessage(), e);
      LOGGER.error("Error: " + e.getMessage(), e);
    } finally {
    }
  }
}
