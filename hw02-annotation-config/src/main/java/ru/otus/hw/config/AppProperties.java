package ru.otus.hw.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class AppProperties implements TestConfig, TestFileNameProvider {

    @Value("${test.RequiredPercentRightAnswersToPass}")
    private int requiredPercentRightAnswersToPass;

    @Value("${test.fileName}")
    private String testFileName;
}
