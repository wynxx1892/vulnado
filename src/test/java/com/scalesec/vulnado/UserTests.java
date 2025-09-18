import com.scalesec.vulnado.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.Test;
import org.mockito.Mockito;

import javax.crypto.SecretKey;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UserTests {

    // Helper method to create a mock ResultSet for testing
    private ResultSet createMockResultSet(String id, String username, String password) throws Exception {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("userid")).thenReturn(id);
        when(resultSet.getString("username")).thenReturn(username);
        when(resultSet.getString("password")).thenReturn(password);
        return resultSet;
    }

    // Test for the User constructor
    @Test
    public void constructor_ShouldInitializeFields() {
        String id = "1";
        String username = "testUser";
        String hashedPassword = "hashedPassword";

        User user = new User(id, username, hashedPassword);

        assertEquals("User ID should match", id, user.id);
        assertEquals("Username should match", username, user.username);
        assertEquals("Hashed password should match", hashedPassword, user.hashedPassword);
    }

    // Test for the token method
    @Test
    public void token_ShouldGenerateValidToken() {
        String secret = "mySecretKey123456789012345678901234567890";
        User user = new User("1", "testUser", "hashedPassword");

        String token = user.token(secret);

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        String subject = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();

        assertEquals("Token subject should match username", user.username, subject);
    }

    // Test for the assertAuth method with valid token
    @Test
    public void assertAuth_ValidToken_ShouldNotThrowException() {
        String secret = "mySecretKey123456789012345678901234567890";
        User user = new User("1", "testUser", "hashedPassword");
        String token = user.token(secret);

        try {
            User.assertAuth(secret, token);
        } catch (Exception e) {
            fail("Valid token should not throw exception");
        }
    }

    // Test for the assertAuth method with invalid token
    @Test(expected = Unauthorized.class)
    public void assertAuth_InvalidToken_ShouldThrowUnauthorized() {
        String secret = "mySecretKey123456789012345678901234567890";
        String invalidToken = "invalidToken";

        User.assertAuth(secret, invalidToken);
    }

    // Test for the fetch method with valid user
    @Test
    public void fetch_ValidUser_ShouldReturnUser() throws Exception {
        String username = "testUser";
        String id = "1";
        String hashedPassword = "hashedPassword";

        Connection mockConnection = mock(Connection.class);
        Statement mockStatement = mock(Statement.class);
        ResultSet mockResultSet = createMockResultSet(id, username, hashedPassword);

        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        Postgres.connection = mockConnection;

        User user = User.fetch(username);

        assertNotNull("User should not be null", user);
        assertEquals("User ID should match", id, user.id);
        assertEquals("Username should match", username, user.username);
        assertEquals("Hashed password should match", hashedPassword, user.hashedPassword);
    }

    // Test for the fetch method with invalid user
    @Test
    public void fetch_InvalidUser_ShouldReturnNull() throws Exception {
        String username = "nonExistentUser";

        Connection mockConnection = mock(Connection.class);
        Statement mockStatement = mock(Statement.class);
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockResultSet.next()).thenReturn(false);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

        Postgres.connection = mockConnection;

        User user = User.fetch(username);

        assertNull("User should be null for non-existent username", user);
    }
}
