package com.scalesec.vulnado;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

// Unit tests for LoginController class
@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTests {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private User mockUser;

    // Test for successful login
    @Test
    public void login_ValidCredentials_ShouldReturnToken() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";
        String hashedPassword = "hashedPassword";
        String token = "testToken";
        String secret = "testSecret";

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.username = username;
        loginRequest.password = password;

        loginController.secret = secret;

        when(mockUser.hashedPassword).thenReturn(hashedPassword);
        when(mockUser.token).thenReturn(token);
        mockStatic(User.class);
        when(User.fetch(username)).thenReturn(mockUser);
        mockStatic(Postgres.class);
        when(Postgres.md5(password)).thenReturn(hashedPassword);

        // Act
        LoginResponse response = loginController.login(loginRequest);

        // Assert
        assertNotNull("Response should not be null", response);
        assertEquals("Token should match", token, response.token);
    }

    // Test for invalid credentials
    @Test(expected = ResponseStatusException.class)
    public void login_InvalidCredentials_ShouldThrowUnauthorized() {
        // Arrange
        String username = "testUser";
        String password = "wrongPassword";
        String hashedPassword = "hashedPassword";
        String secret = "testSecret";

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.username = username;
        loginRequest.password = password;

        loginController.secret = secret;

        when(mockUser.hashedPassword).thenReturn(hashedPassword);
        mockStatic(User.class);
        when(User.fetch(username)).thenReturn(mockUser);
        mockStatic(Postgres.class);
        when(Postgres.md5(password)).thenReturn("wrongHashedPassword");

        // Act
        loginController.login(loginRequest);

        // Assert
        // Exception is expected
    }

    // Test for null user
    @Test(expected = ResponseStatusException.class)
    public void login_NullUser_ShouldThrowUnauthorized() {
        // Arrange
        String username = "nonExistentUser";
        String password = "testPassword";
        String secret = "testSecret";

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.username = username;
        loginRequest.password = password;

        loginController.secret = secret;

        mockStatic(User.class);
        when(User.fetch(username)).thenReturn(null);

        // Act
        loginController.login(loginRequest);

        // Assert
        // Exception is expected
    }
}
