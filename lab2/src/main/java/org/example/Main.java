package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import java.io.InputStream;
import java.util.Properties;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        logger.info("Программа запущена");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите строку: ");
        String input = scanner.nextLine();

        String result = StringProcessor.process(input);

        logger.info("Результат обработки строки: {}", result);

        logger.info("Программа завершена");

        try (InputStream is = Main.class.getClassLoader()
                .getResourceAsStream("build-passport.properties")) {

            if (is != null) {
                Properties props = new Properties();
                props.load(is);

                logger.info("Паспорт сборки:");

                for (String key : props.stringPropertyNames()) {
                    String value = props.getProperty(key);
                    logger.info("{} = {}", key, value);
                }
            }

        } catch (Exception e) {
            logger.error("Ошибка чтения build-passport", e);
        }
    }
}


