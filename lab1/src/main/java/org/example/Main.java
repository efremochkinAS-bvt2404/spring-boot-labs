package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        Person p1 = new Person("Alex", 20);

        String json = JsonUtil.toJson(p1);
        logger.info("JSON:\n{}", json);

        Person p2 = JsonUtil.fromJson(json, Person.class);
        logger.info("Объект из JSON: name={}, age={}", p2.name, p2.age);
    }
}

