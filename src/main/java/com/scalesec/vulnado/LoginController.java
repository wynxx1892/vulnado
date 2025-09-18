package com.scalesec.vulnado;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.beans.factory.annotation.*;
import java.io.Serializable;

@RestController
@EnableAutoConfiguration
public class LoginController {
  @Value("${app.secret}")
  private String secret;

  @CrossOrigin(origins = "https://trusted-domain.com")
  @PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
  LoginResponse login(@RequestBody LoginRequest input) {
    User user = User.fetch(input.username);
    if (Postgres.md5(input.password).equals(user.hashedPassword)) {
      return new LoginResponse(user.token(secret));
    } else {
      throw new Unauthorized("Access Denied");
    }
  }
}

class LoginRequest implements Serializable {
  private String username;
public String getUsername() {
  private String password;
    return username;
}
}

public void setUsername(String username) {
class LoginResponse implements Serializable {
    this.username = username;
  private String token;
}
  public LoginResponse(String msg) { this.token = msg; }
public String getPassword() {
}
    return password;

}
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public void setPassword(String password) {
class Unauthorized extends RuntimeException {
    this.password = password;
  public Unauthorized(String exception) {
}
    super(exception);
public String getToken() {
  }
    return token;
}
}
