package com.scalesec.vulnado;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import org.springframework.http.HttpStatus;
import org.springframework.boot.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.io.Serializable;
import org.springframework.stereotype.Controller;

import org.springframework.beans.factory.annotation.Value;
@RestController
import java.io.Serializable;
@EnableAutoConfiguration
import java.util.Objects;
public class LoginController {
  @RestController
@EnableAutoConfiguration
  private String secret;
public class LoginController {
    @Value("${app.secret}")
    private String secret;
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public LoginResponse login(@RequestBody LoginRequest input) {
        User user = User.fetch(input.username);
        if (Objects.equals(Postgres.md5(input.password), user.hashedPassword)) {
            return new LoginResponse(user.token, secret);
        } else {
            throw new Unauthorized("Access Denied");
        }
  } // End of LoginController
  class LoginRequest implements Serializable {
    public String username;
    public String password;
  } // End of LoginRequest
  class LoginResponse implements Serializable {
    public String token;
    public String secret;

    public LoginResponse(String token, String secret) {
        this.token = token;
        this.secret = secret;
    }
} // End of LoginResponse
@ResponseStatus(HttpStatus.UNAUTHORIZED)