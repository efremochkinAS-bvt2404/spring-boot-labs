package org.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Привет, Spring Boot!";
    }

    @GetMapping("/goodbye")
    public String sayGoodbye() {
        return "До свидания, Spring Boot!";
    }

    @GetMapping("/greet")
    public String greet(@RequestParam(required = false) String name) {
        if (name == null || name.isBlank()) {
            return "Привет, гость!";
        }
        return "Привет, " + name + "!";
    }

    @GetMapping("/info")
    public String info(@RequestParam(required = false) String name,
                       @RequestParam(required = false) Integer age) {

        String output = "";

        if (name != null && !name.isBlank()) {
            output += "Ваше имя: " + name + "<br>";
        } else {
            output += "Имя не указано!<br>";
        }

        if (age != null) {
            output += "Ваш возраст: " + age;
        } else {
            output += "Возраст не указан!";
        }

        return output;
    }



}