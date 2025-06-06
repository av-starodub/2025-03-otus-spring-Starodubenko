package ru.otus.hw.matcher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestConfig;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("PercentPassingMarcherTest")
class PercentPassingMatcherTest {

    private static final int REQUIRED_PERCENT_TO_PASS = 80;

    @Mock
    private TestConfig testConfig;

    private PercentPassingMarcher matcher;

    @BeforeEach
    void setUp() {
        when(testConfig.getRequiredPercentRightAnswersToPass()).thenReturn(REQUIRED_PERCENT_TO_PASS);
        matcher = new PercentPassingMarcher(testConfig);
    }

    @ParameterizedTest(name = "right={0}, total={1} → expected={2}")
    @CsvSource({
            "3,  5, false",  // 60 %
            "4,  5, true",   // 80 %
            "5,  5, true",   // 100 %
            "8, 10, true",   // 80 %
            "7, 10, false"   // 70 %
    })
    @DisplayName("Should match test result by percent correctly")
    void shouldMatchByPercent(int right, int total, boolean expected) {
        assertThat(matcher.isPassed(right, total)).isEqualTo(expected);
    }

}
