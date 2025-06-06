package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.matcher.ResultMatcher;

@Service
@RequiredArgsConstructor
public class ResultServiceImpl implements ResultService {

    private final LocalizedIOService ioService;

    private final ResultMatcher resultMatcher;

    @Override
    public void showResult(TestResult testResult) {
        var studentFullName = testResult.getStudentFullName();
        var rightAnswerCount = testResult.getRightAnswersCount();
        var testSize = testResult.getTestSize();

        ioService.printLine("");
        ioService.printLineLocalized("ResultService.test.results");
        ioService.printFormattedLineLocalized("ResultService.student", studentFullName);
        ioService.printFormattedLineLocalized("ResultService.answered.questions.count", testSize);
        ioService.printFormattedLineLocalized("ResultService.right.answers.count", rightAnswerCount);

        if (resultMatcher.isPassed(rightAnswerCount, testSize)) {
            ioService.printLineLocalized("ResultService.passed.test");
            return;
        }
        ioService.printLineLocalized("ResultService.fail.test");
    }

}
