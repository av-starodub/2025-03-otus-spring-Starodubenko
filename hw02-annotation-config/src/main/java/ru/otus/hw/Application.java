package ru.otus.hw;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.hw.service.TestRunnerService;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan({"ru.otus.hw.config", "ru.otus.hw.dao", "ru.otus.hw.service"})
public class Application {
    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext(Application.class);
        var testRunnerService = context.getBean(TestRunnerService.class);
        testRunnerService.run();

    }
}
