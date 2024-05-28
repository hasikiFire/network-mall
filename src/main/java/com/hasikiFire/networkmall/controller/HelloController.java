package com.hasikiFire.networkmall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/helloworld")
    public String helloWorld() {
        return "helloworld";
    }

    @RequestMapping("/user")
    public Map getUsers() {
        Map map = new HashMap<>();
        map.put("name", "fangxiao");
        map.put("age", "15");
        return map;
    }
}
