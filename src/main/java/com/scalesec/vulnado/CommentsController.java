package com.scalesec.vulnado;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.beans.factory.annotation.*;
import java.util.List;
import org.springframework.boot.autoconfigure.*;
import java.io.Serializable;
import java.util.List;
import java.io.Serializable;

@RestController
@EnableAutoConfiguration
public class CommentsController {
    @Value("${app.secret}")
    private String secret;

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/comments", produces = "application/json")
    public List<Comment> comments(@RequestHeader(value = "x-auth-token") String token) {
        User.assertAuth(secret, token);
        return Comment.fetchAll();
  }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/comments", produces = "application/json", consumes = "application/json")
    public Comment createComment(@RequestHeader(value = "x-auth-token") String token, @RequestBody CommentRequest input) {
        User.assertAuth(secret, token);
        return Comment.create(input.username, input.body);
  }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/comments/{id}", produces = "application/json")
    public Boolean deleteComment(@RequestHeader(value = "x-auth-token") String token, @PathVariable("id") String id) {
        User.assertAuth(secret, token);
        return Comment.delete(id);
}

} // End of CommentsController
  class CommentRequest implements Serializable {
    public String username;
    public String body;

} // End of CommentRequest
@ResponseStatus(HttpStatus.BAD_REQUEST)
  class BadRequest extends RuntimeException {
    public BadRequest(String exception) {
        super(exception);
    }

} // End of BadRequest
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  class ServerError extends RuntimeException {
    public ServerError(String exception) {
        super(exception);
    }
} // End of ServerError