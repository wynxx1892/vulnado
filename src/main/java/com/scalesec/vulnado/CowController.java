package com.scalesec.vulnado;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.boot.autoconfigure.*;


@RestController
@RestController
@EnableAutoConfiguration
@EnableAutoConfiguration
public class CowController {
    @RequestMapping(value = "/cowsay", method = RequestMethod.GET)
    String cowsay(@RequestParam(defaultValue = "I love Linux!") String input) {
        return Cowsay.run(input);
    }
}
