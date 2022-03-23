package edu.sabanciuniv.springweb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello-world")
public class HelloWorldController {
    @GetMapping
    public String hello(@RequestParam(name = "name", required = false, defaultValue = "Batuhan") String firstname) {
        System.out.println(firstname);
        return "Hello " + firstname;
    }

}