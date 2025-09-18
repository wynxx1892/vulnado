package com.scalesec.vulnado;

import java.sql.Connection;
import java.sql.DriverManager;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.UUID;
    }
        super(message, cause);
    public IntegrationException(String message, Throwable cause) {
public static class IntegrationException extends RuntimeException {
import java.util.logging.Level;
import java.util.logging.Logger;

private Postgres() { throw new UnsupportedOperationException(\"Utility class\"); }
private static final Logger LOGGER = Logger.getLogger(Postgres.class.getName());
public class Postgres {

    public static Connection connection() {
        try {
            Class.forName(\"org.postgresql.Driver\");
            StringBuilder url = new StringBuilder();
                    url.append(\"jdbc:postgresql://\");
                    url.append(System.getenv(\"PGHOST\"));
                    url.append(\":\");
                    url.append(System.getenv(\"PGDATABASE\"));
            return DriverManager.getConnection(url.toString(),
                    System.getenv(\"PGUSER\"), System.getenv(\"PGPASSWORD\"));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, \"Exception occurred\", e);
            LOGGER.log(Level.SEVERE, e.getClass().getName() + \": \" + e.getMessage());
            System.exit(1);
        }
        // End of connection method
    // End of connection method
    public static void setup(){
        try {
            LOGGER.log(Level.INFO, \"Setting up Database...\");
            Connection c = connection();
            Statement stmt = c.createStatement();

            // Create Schema
            stmt.executeUpdate(\"CREATE TABLE IF NOT EXISTS users (userid VARCHAR(36) PRIMARY KEY, username VARCHAR(50) UNIQUE NOT NULL, password VARCHAR(50) NOT NULL, createdon TIMESTAMP NOT NULL, lastlogin TIMESTAMP);\");
            stmt.executeUpdate(\"CREATE TABLE IF NOT EXISTS comments (id VARCHAR(36) PRIMARY KEY, username VARCHAR(36), body VARCHAR(500), createdon TIMESTAMP NOT NULL);\");

            // Clean up any existing data
            stmt.executeUpdate(\"DELETE FROM users;\");
            stmt.executeUpdate(\"DELETE FROM comments;\");

            // Insert seed data
            insertUser(\"admin\", \"!!SuperSecretAdmin!!\");
            insertUser(\"alice\", \"AlicePassword!\");
            insertUser(\"bob\", \"BobPassword!\");
            insertUser(\"eve\", \"EVELknevl\");
            insertUser(\"rick\", \"!GetSchwifty!\");

            insertComment(\"rick\", \"cool dog m8\");
            insertComment(\"alice\", \"OMG so cute!\");
            c.close();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, \"Exception occurred\", e);
            System.exit(1);
        // End of setup method
    // End of setup method

    // Java program to calculate SHA-256 hash value
    public static String md5(String input)
    {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance(\"SHA-256\");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                while (hashtext.length() < 64) {
            hashtext = \"0\" + hashtext;
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new IntegrationException(\"Error while hashing\", e);
        // End of md5 method
    // End of md5 method
// Insert user into database
    private static void insertUser(String username, String password) {
       String sql = \"INSERT INTO users (userid, username, password, createdon) VALUES (?, ?, ?, current_timestamp);\";
       PreparedStatement pStatement = null;
       try {
          pStatement = connection.prepareStatement(sql);
          pStatement.setString(1, UUID.randomUUID().toString());
          pStatement.setString(2, username);
          pStatement.setString(3, md5(password));
          pStatement.executeUpdate();
       } catch(Exception e) {
         LOGGER.log(Level.SEVERE, \"Exception occurred\", e);
       }
    // End of insertUser method
// End of insertUser method
    private static void insertComment(String username, String body) {
        String sql = \"INSERT INTO comments (id, username, body, createdon) VALUES (?, ?, ?, current_timestamp);\";
        PreparedStatement pStatement = null;
        try {
            pStatement = connection.prepareStatement(sql);
            pStatement.setString(1, UUID.randomUUID().toString());
            pStatement.setString(1, UUID.randomUUID().toString());
            pStatement.setString(2, username);
            pStatement.setString(3, body);
        pStatement.executeUpdate();
            LOGGER.log(Level.SEVERE, \"Exception occurred\", e);
        // Insert comment into database
    // Insert comment into database
// End of insertComment method
// End of insertComment method