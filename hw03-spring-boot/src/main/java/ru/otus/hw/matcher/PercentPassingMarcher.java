package ru.otus.hw.matcher;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;
import ru.otus.hw.config.TestConfig;

@Service
@RequiredArgsConstructor
@ConditionalOnExpression("#{${test.requiredPercentRightAnswersToPass:0} > 0}")
public class PercentPassingMarcher implements ResultMatcher {

    private final TestConfig testConfig;

    @Override
    public boolean isPassed(int rightAnswerCount, int testSize) {
        var requiredRightAnswerPercentToPass = testConfig.getRequiredPercentRightAnswersToPass();
        var requiredAnswersToPass = Math.ceil((double) testSize * requiredRightAnswerPercentToPass / 100);
        return rightAnswerCount >= requiredAnswersToPass;
    }

}
