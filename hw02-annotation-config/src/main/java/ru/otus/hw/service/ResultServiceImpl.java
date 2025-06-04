package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.config.TestConfig;
import ru.otus.hw.domain.TestResult;

@RequiredArgsConstructor
@Service
public class ResultServiceImpl implements ResultService {

    private final TestConfig testConfig;

    private final IOService ioService;

    @Override
    public void showResult(TestResult testResult) {
        var studentFullName = testResult.getStudentFullName();
        var rightAnswerCount = testResult.getRightAnswersCount();
        var testSize = testResult.getTestSize();
        var requiredRightAnswerPercentToPass = testConfig.getRequiredPercentRightAnswersToPass();

        var isPassed = isPassed(rightAnswerCount, testSize, requiredRightAnswerPercentToPass);

        ioService.printLine("");
        ioService.printLine("Test results: ");
        ioService.printFormattedLine("Student: %s", studentFullName);
        ioService.printFormattedLine("Answered questions count: %d", testSize);
        ioService.printFormattedLine("Right answers count: %d", rightAnswerCount);

        if (isPassed) {
            ioService.printLine("Congratulations! You passed test!");
            return;
        }
        ioService.printLine("Sorry. You fail test.");
    }

    private boolean isPassed(int rightAnswerCount, int testSize, int requiredRightAnswerPercentToPass) {
        var requiredAnswersToPass = Math.ceil((double) testSize * requiredRightAnswerPercentToPass / 100);
        return rightAnswerCount >= requiredAnswersToPass;
    }
}
