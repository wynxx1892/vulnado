package com.scalesec.vulnado;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class CommentsControllerTests {

    @InjectMocks
    private CommentsController commentsController;

    @Mock
    private Comment mockComment;

    @Mock
    private User mockUser;

    private final String validToken = "validToken";
    private final String invalidToken = "invalidToken";
    private final String secret = "secretKey";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        commentsController = new CommentsController();
        commentsController.secret = secret;
    }

    // Test for GET /comments
    @Test
    public void comments_ValidToken_ShouldReturnComments() {
        // Arrange
        List<Comment> mockComments = new ArrayList<>();
        mockComments.add(new Comment("user1", "This is a comment."));
        when(mockUser.assertAuth(secret, validToken)).thenReturn(true);
        when(Comment.fetchall()).thenReturn(mockComments);

        // Act
        List<Comment> result = commentsController.comments(validToken);

        // Assert
        assertNotNull("Result should not be null", result);
        assertEquals("Result size should match", 1, result.size());
        assertEquals("Comment body should match", "This is a comment.", result.get(0).getBody());
        verify(mockUser).assertAuth(secret, validToken);
    }

    @Test(expected = ResponseStatusException.class)
    public void comments_InvalidToken_ShouldThrowException() {
        // Arrange
        when(mockUser.assertAuth(secret, invalidToken)).thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        // Act
        commentsController.comments(invalidToken);

        // Assert
        // Exception is expected
    }

    // Test for POST /comments
    @Test
    public void createComment_ValidToken_ShouldCreateComment() {
        // Arrange
        CommentRequest input = new CommentRequest();
        input.username = "user1";
        input.body = "New comment";
        Comment mockCreatedComment = new Comment("user1", "New comment");
        when(mockUser.assertAuth(secret, validToken)).thenReturn(true);
        when(Comment.create(input.username, input.body)).thenReturn(mockCreatedComment);

        // Act
        Comment result = commentsController.createComment(validToken, input);

        // Assert
        assertNotNull("Result should not be null", result);
        assertEquals("Username should match", "user1", result.getUsername());
        assertEquals("Comment body should match", "New comment", result.getBody());
        verify(mockUser).assertAuth(secret, validToken);
    }

    @Test(expected = ResponseStatusException.class)
    public void createComment_InvalidToken_ShouldThrowException() {
        // Arrange
        CommentRequest input = new CommentRequest();
        input.username = "user1";
        input.body = "New comment";
        when(mockUser.assertAuth(secret, invalidToken)).thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        // Act
        commentsController.createComment(invalidToken, input);

        // Assert
        // Exception is expected
    }

    // Test for DELETE /comments/{id}
    @Test
    public void deleteComment_ValidToken_ShouldDeleteComment() {
        // Arrange
        String commentId = "123";
        when(mockUser.assertAuth(secret, validToken)).thenReturn(true);
        when(Comment.delete(commentId)).thenReturn(true);

        // Act
        Boolean result = commentsController.deleteComment(validToken, commentId);

        // Assert
        assertTrue("Comment should be deleted successfully", result);
        verify(mockUser).assertAuth(secret, validToken);
    }

    @Test(expected = ResponseStatusException.class)
    public void deleteComment_InvalidToken_ShouldThrowException() {
        // Arrange
        String commentId = "123";
        when(mockUser.assertAuth(secret, invalidToken)).thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        // Act
        commentsController.deleteComment(invalidToken, commentId);

        // Assert
        // Exception is expected
    }
}
