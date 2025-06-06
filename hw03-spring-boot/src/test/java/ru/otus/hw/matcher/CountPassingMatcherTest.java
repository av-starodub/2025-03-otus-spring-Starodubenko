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
@DisplayName("CountPassingMatcherTest")
class CountPassingMatcherTest {

    private static final int REQUIRED_COUNT_TO_PASS = 3;

    @Mock
    private TestConfig testConfig;

    private CountPassingMatcher matcher;

    @BeforeEach
    void setUp() {
        when(testConfig.getRightAnswersCountToPass()).thenReturn(REQUIRED_COUNT_TO_PASS);
        matcher = new CountPassingMatcher(testConfig);
    }

    @ParameterizedTest(name = "right={0}, total={1} → expected={2}")
    @CsvSource({
            "0, 5, false",
            "2, 5, false",
            "3, 5, true",
            "4, 5, true"
    })
    @DisplayName("Should match test result by count correctly")
    void shouldMatchByCount(int right, int total, boolean expected) {
        assertThat(matcher.isPassed(right, total)).isEqualTo(expected);
    }

}
