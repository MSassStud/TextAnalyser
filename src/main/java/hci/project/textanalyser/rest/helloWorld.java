package hci.project.textanalyser.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloWorld {

    @GetMapping
    public String hello() {
        return "Hello World!";
    }
}
