package com.scalesec.vulnado;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.Statement;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.security.MessageDigest;
import java.util.UUID;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.sql.PreparedStatement;
import java.util.logging.Logger;
import java.sql.Statement;
import java.security.MessageDigest;
import java.util.UUID;
import java.security.NoSuchAlgorithmException;

import java.math.BigInteger;
public class Postgres {
public class Postgres {
    private static final Logger LOGGER = Logger.getLogger(Postgres.class.getName());
    public static Connection connection() {
    private static Connection connection;
        try {
    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = new StringBuilder()
                .append("jdbc:postgresql://")
                .append(System.getenv("PGHOST"))
                .append(":")
                .append(System.getenv("PGPORT"))
                    System.getenv("PGUSER"), System.getenv("PGPASSWORD"));
                .append("/")
                .append(System.getenv("PGDATABASE"))
                .toString();
            return DriverManager.getConnection(url, System.getenv("PGUSER"), System.getenv("PGPASSWORD"));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Connection failed: {0}", e.getMessage());
            System.exit(1);
        }
        return null;
    }
    public static void setup() {
        try (Connection c = getConnection(); Statement stmt = c.createStatement()) {
            LOGGER.info("Setting up Database...");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (userid VARCHAR(36) PRIMARY KEY, username VARCHAR(50) UNIQUE NOT NULL, password VARCHAR(64) NOT NULL, createdon TIMESTAMP NOT NULL, lastlogin TIMESTAMP)");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS comments (id VARCHAR(36) PRIMARY KEY, username VARCHAR(36), body VARCHAR(500), createdon TIMESTAMP NOT NULL)");
            stmt.executeUpdate("DELETE FROM users");
            stmt.executeUpdate("DELETE FROM comments");
            insertUser("admin", System.getenv("ADMIN_PASSWORD"));
            insertUser("alice", System.getenv("ALICE_PASSWORD"));
            insertUser("bob", System.getenv("BOB_PASSWORD"));
            insertUser("eve", System.getenv("EVE_PASSWORD"));
            insertUser("rick", System.getenv("RICK_PASSWORD"));
            insertComment("rick", "cool dog m8");
            insertComment("alice", "OMG so cute!");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Setup failed: {0}", e.getMessage());
            System.exit(1);
        }
    }
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashtext = new StringBuilder(no.toString(16));
            while (hashtext.length() < 64) {
                hashtext.insert(0, "0");
            }
            return hashtext.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    private static void insertUser(String username, String password) {
        String sql = "INSERT INTO users (userid, username, password, createdon) VALUES (?, ?, ?, current_timestamp)";
        try (PreparedStatement pStatement = connection.prepareStatement(sql)) {
            pStatement.setString(1, UUID.randomUUID().toString());
            pStatement.setString(2, username);
            pStatement.setString(3, md5(password));
            pStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Insert user failed: {0}", e.getMessage());
        }
    }
    private static void insertComment(String username, String body) {
        String sql = "INSERT INTO comments (id, username, body, createdon) VALUES (?, ?, ?, current_timestamp)";
        try (PreparedStatement pStatement = connection.prepareStatement(sql)) {
            pStatement.setString(1, UUID.randomUUID().toString());
            pStatement.setString(2, username);
            pStatement.setString(3, body);
            pStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Insert comment failed: {0}", e.getMessage());
        }
    }