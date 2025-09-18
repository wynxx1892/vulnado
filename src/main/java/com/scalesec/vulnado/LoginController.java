package com.scalesec.vulnado;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;
@RestController
@EnableAutoConfiguration
import java.io.Serializable;
@EnableAutoConfiguration
import java.security.MessageDigest;
public class LoginController {
import java.security.NoSuchAlgorithmException;
    @Value("${app.secret}")
    private String secret;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public LoginResponse login(@RequestBody LoginRequest input) {
        User user = User.fetch(input.username);
        if (user != null && validatePassword(input.password, user.hashedPassword)) {
            return new LoginResponse(user.token, secret);
        } else {
            throw new Unauthorized("Access Denied");
        }
    }
    }
  }
    
}
    private boolean validatePassword(String password, String hashedPassword) {

        try {
}
            MessageDigest md = MessageDigest.getInstance("MD5");
  class LoginRequest implements Serializable {
            md.update(password.getBytes());
    public String username;
            byte[] digest = md.digest();
    public String password;
}
            StringBuilder sb = new StringBuilder();

            for (byte b : digest) {
class LoginResponse implements Serializable {
                sb.append(String.format("%02x", b));
    public String token;
    public String secret;
            }
    public LoginResponse(String token, String secret) {
            return sb.toString().equals(hashedPassword);
        this.token = token;
        this.secret = secret;
        } catch (NoSuchAlgorithmException e) {

    }
            throw new RuntimeException("Error validating password", e);
}
        }
@ResponseStatus(HttpStatus.UNAUTHORIZED)
    }
  class Unauthorized extends RuntimeException {
    public Unauthorized(String exception) {
        super(exception);
    }
}
