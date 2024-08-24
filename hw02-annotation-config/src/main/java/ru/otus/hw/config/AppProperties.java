package ru.otus.hw.config;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;

@Data
@Configuration
@PropertySource("classpath:application.properties")
public class AppProperties implements TestConfig, TestFileNameProvider {

    @Value("${test.RequiredPercentRightAnswersToPass}")
    private int requiredPercentRightAnswersToPass;

    @Value("${test.fileName}")
    private String testFileName;
}
