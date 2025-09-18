package com.scalesec.vulnado;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import java.io.Serializable;
import java.io.Serializable;

@RestController
@EnableAutoConfiguration
public class CowController {
    @RequestMapping(value = "/cowsay")
    public String cowsay(@RequestParam(defaultValue = "I love Linux!") String input) {
        return Cowsay.run(input);
    }
    }
}
}
