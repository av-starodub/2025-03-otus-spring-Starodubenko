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
        var studentFullName = testResult.getStudent().getFullName();
        var rightAnswerCount = testResult.getRightAnswersCount();
        var answeredQuestions = testResult.getAnsweredQuestions().size();
        var requiredRightAnswerPercentToPass = testConfig.getRequiredPercentRightAnswersToPass();

        var isPassed = isPassed(rightAnswerCount, answeredQuestions, requiredRightAnswerPercentToPass);

        ioService.printLine("");
        ioService.printLine("Test results: ");
        ioService.printFormattedLine("Student: %s", studentFullName);
        ioService.printFormattedLine("Answered questions count: %d", answeredQuestions);
        ioService.printFormattedLine("Right answers count: %d", rightAnswerCount);

        if (isPassed) {
            ioService.printLine("Congratulations! You passed test!");
            return;
        }
        ioService.printLine("Sorry. You fail test.");
    }

    private boolean isPassed(int rightAnswerCount, int answeredQuestions, int requiredRightAnswerPercentToPass) {
        var requiredAnswersToPass = Math.ceil(
                (double) answeredQuestions * requiredRightAnswerPercentToPass / 100
        );
        return rightAnswerCount >= requiredAnswersToPass;
    }
}
