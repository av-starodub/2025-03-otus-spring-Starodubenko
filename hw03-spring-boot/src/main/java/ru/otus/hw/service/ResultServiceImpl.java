package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.config.TestConfig;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final TestConfig testConfig;

    private final LocalizedIOService ioService;

    @Override
    public void showResult(TestResult testResult) {
        var studentFullName = testResult.getStudentFullName();
        var rightAnswerCount = testResult.getRightAnswersCount();
        var testSize = testResult.getTestSize();

        var isPassed = isPassed(rightAnswerCount, testSize);

        ioService.printLine("");
        ioService.printLineLocalized("ResultService.test.results");
        ioService.printFormattedLineLocalized("ResultService.student", studentFullName);
        ioService.printFormattedLineLocalized("ResultService.answered.questions.count", testSize);
        ioService.printFormattedLineLocalized("ResultService.right.answers.count", rightAnswerCount);

        if (isPassed) {
            ioService.printLineLocalized("ResultService.passed.test");
            return;
        }
        ioService.printLineLocalized("ResultService.fail.test");
    }

    private boolean isPassed(int rightAnswerCount, int testSize) {
        var requiredRightAnswerPercentToPass = testConfig.getRequiredPercentRightAnswersToPass();
        if (requiredRightAnswerPercentToPass > 0) {
            var requiredAnswersToPass = Math.ceil((double) testSize * requiredRightAnswerPercentToPass / 100);
            return rightAnswerCount >= requiredAnswersToPass;
        } else {
            return rightAnswerCount >= testConfig.getRightAnswersCountToPass();
        }
    }

}
