package com.scalesec.vulnado;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CommentTests {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private Statement mockStatement;
    private ResultSet mockResultSet;

    @Before
    public void setUp() throws Exception {
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockStatement = mock(Statement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    // Helper method to mock ResultSet for fetchAll
    private void mockResultSetForFetchAll() throws Exception {
        when(mockResultSet.next()).thenReturn(true, false); // Simulate one row
        when(mockResultSet.getString("id")).thenReturn(UUID.randomUUID().toString());
        when(mockResultSet.getString("username")).thenReturn("test_user");
        when(mockResultSet.getString("body")).thenReturn("This is a test comment.");
        when(mockResultSet.getTimestamp("createdon")).thenReturn(new Timestamp(new Date().getTime()));
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);
    }

    @Test
    public void create_ValidInput_ShouldReturnComment() throws Exception {
        // Arrange
        String username = "test_user";
        String body = "This is a test comment.";
        long time = new Date().getTime();
        Timestamp timestamp = new Timestamp(time);

        Comment mockComment = spy(new Comment(UUID.randomUUID().toString(), username, body, timestamp));
        doReturn(true).when(mockComment).commit();

        // Act
        Comment result = Comment.create(username, body);

        // Assert
        assertNotNull("Comment should not be null", result);
        assertEquals("Username should match", username, result.username);
        assertEquals("Body should match", body, result.body);
        assertNotNull("CreatedOn should not be null", result.createdon);
    }

    @Test(expected = RuntimeException.class)
    public void create_CommitFails_ShouldThrowException() throws Exception {
        // Arrange
        String username = "test_user";
        String body = "This is a test comment.";
        Comment mockComment = spy(new Comment(UUID.randomUUID().toString(), username, body, new Timestamp(new Date().getTime())));
        doReturn(false).when(mockComment).commit();

        // Act
        Comment.create(username, body);
    }

    @Test
    public void fetchAll_ValidData_ShouldReturnComments() throws Exception {
        // Arrange
        mockResultSetForFetchAll();

        // Act
        List<Comment> comments = Comment.fetchAll();

        // Assert
        assertNotNull("Comments list should not be null", comments);
        assertEquals("Comments list size should be 1", 1, comments.size());
        assertEquals("Username should match", "test_user", comments.get(0).username);
    }

    @Test
    public void delete_ValidId_ShouldReturnTrue() throws Exception {
        // Arrange
        String id = UUID.randomUUID().toString();
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Act
        boolean result = Comment.delete(id);

        // Assert
        assertTrue("Delete should return true for valid ID", result);
    }

    @Test
    public void delete_InvalidId_ShouldReturnFalse() throws Exception {
        // Arrange
        String id = UUID.randomUUID().toString();
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        // Act
        boolean result = Comment.delete(id);

        // Assert
        assertFalse("Delete should return false for invalid ID", result);
    }

    @Test
    public void commit_ValidData_ShouldReturnTrue() throws Exception {
        // Arrange
        Comment comment = new Comment(UUID.randomUUID().toString(), "test_user", "This is a test comment.", new Timestamp(new Date().getTime()));
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Act
        boolean result = comment.commit();

        // Assert
        assertTrue("Commit should return true for valid data", result);
    }

    @Test
    public void commit_InvalidData_ShouldThrowSQLException() throws Exception {
        // Arrange
        Comment comment = new Comment(UUID.randomUUID().toString(), "test_user", "This is a test comment.", new Timestamp(new Date().getTime()));
        when(mockPreparedStatement.executeUpdate()).thenThrow(new RuntimeException("SQL Exception"));

        // Act & Assert
        try {
            comment.commit();
            fail("Commit should throw SQLException for invalid data");
        } catch (Exception e) {
            assertTrue("Exception message should contain 'SQL Exception'", e.getMessage().contains("SQL Exception"));
        }
    }
}
