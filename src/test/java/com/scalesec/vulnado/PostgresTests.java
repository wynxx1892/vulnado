import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PostgresTests {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private Statement mockStatement;

    @Before
    public void setUp() throws Exception {
        // Mocking the database connection and related objects
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockStatement = mock(Statement.class);

        // Mocking the behavior of the connection
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Setting the mocked connection in the Postgres class
        Postgres.connection = mockConnection;
    }

    @Test
    public void connection_ShouldReturnValidConnection() {
        try {
            Connection connection = Postgres.connection;
            assertNotNull("Connection should not be null", connection);
        } catch (Exception e) {
            fail("Exception should not be thrown while getting connection: " + e.getMessage());
        }
    }

    @Test
    public void setup_ShouldCreateTablesAndInsertSeedData() {
        try {
            Postgres.setup();

            // Verify that the statements to create tables were executed
            verify(mockStatement, times(1)).executeUpdate(contains("CREATE TABLE IF NOT EXISTS users"));
            verify(mockStatement, times(1)).executeUpdate(contains("CREATE TABLE IF NOT EXISTS comments"));

            // Verify that the cleanup statements were executed
            verify(mockStatement, times(1)).executeUpdate(contains("DELETE FROM users"));
            verify(mockStatement, times(1)).executeUpdate(contains("DELETE FROM comments"));

            // Verify that the seed data was inserted
            verify(mockPreparedStatement, atLeast(5)).executeUpdate();
        } catch (Exception e) {
            fail("Exception should not be thrown during setup: " + e.getMessage());
        }
    }

    @Test
    public void md5_ShouldReturnCorrectHash() {
        String input = "test";
        String expectedHash = "098f6bcd4621d373cade4e832627b4f6"; // Precomputed MD5 hash for "test"

        String actualHash = Postgres.md5(input);

        assertEquals("MD5 hash should match the expected value", expectedHash, actualHash);
    }

    @Test
    public void insertUser_ShouldInsertUserIntoDatabase() {
        try {
            String username = "testUser";
            String password = "testPassword";

            Postgres.insertUser(username, password);

            // Verify that the prepared statement was executed
            verify(mockPreparedStatement, times(1)).executeUpdate();

            // Verify that the correct values were set in the prepared statement
            verify(mockPreparedStatement, times(1)).setString(eq(1), anyString());
            verify(mockPreparedStatement, times(1)).setString(eq(2), eq(username));
            verify(mockPreparedStatement, times(1)).setString(eq(3), eq(Postgres.md5(password)));
        } catch (Exception e) {
            fail("Exception should not be thrown during insertUser: " + e.getMessage());
        }
    }

    @Test
    public void insertComment_ShouldInsertCommentIntoDatabase() {
        try {
            String username = "testUser";
            String body = "This is a test comment";

            Postgres.insertComment(username, body);

            // Verify that the prepared statement was executed
            verify(mockPreparedStatement, times(1)).executeUpdate();

            // Verify that the correct values were set in the prepared statement
            verify(mockPreparedStatement, times(1)).setString(eq(1), anyString());
            verify(mockPreparedStatement, times(1)).setString(eq(2), eq(username));
            verify(mockPreparedStatement, times(1)).setString(eq(3), eq(body));
        } catch (Exception e) {
            fail("Exception should not be thrown during insertComment: " + e.getMessage());
        }
    }
}
