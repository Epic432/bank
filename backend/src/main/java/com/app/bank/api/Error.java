package com.app.bank.api;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RequestMapping("/error")
@RestController

public class Error {

    @GetMapping("/error")
    public String error() {
        return "This is the java backend of the demo bank app";
    }

}
