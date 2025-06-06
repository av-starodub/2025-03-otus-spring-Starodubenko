package ru.otus.hw.matcher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.otus.hw.config.TestConfig;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ResultMatcherConditionalLoadingTest")
class ResultMatcherConditionalLoadingTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withUserConfiguration(MatcherTestConfig.class);

    @Test
    @DisplayName("Should load PercentPassingMarcher when no requiredPercentRightAnswersToPass property is set")
    void checkLoadPercentMatcher() {
        contextRunner
                .withPropertyValues("test.requiredPercentRightAnswersToPass=80", "test.rightAnswersCountToPass=3")
                .run(ctx -> {
                    assertThat(ctx).hasSingleBean(ResultMatcher.class);
                    assertThat(ctx.getBean(ResultMatcher.class)).isInstanceOf(PercentPassingMarcher.class);
                });
    }

    @Test
    @DisplayName("Should load CountPassingMatcher when requiredPercentRightAnswersToPass property is set to 0")
    void checkLoadCountMatcher() {
        contextRunner
                .withPropertyValues("test.requiredPercentRightAnswersToPass=0", "test.rightAnswersCountToPass=3")
                .run(ctx -> {
                    assertThat(ctx).hasSingleBean(ResultMatcher.class);
                    assertThat(ctx.getBean(ResultMatcher.class)).isInstanceOf(CountPassingMatcher.class);
                });
    }


    @Configuration(proxyBeanMethods = false)
    @Import({CountPassingMatcher.class, PercentPassingMarcher.class})
    static class MatcherTestConfig {

        @Bean
        static PropertySourcesPlaceholderConfigurer properties() {
            return new PropertySourcesPlaceholderConfigurer();
        }

        @Bean
        TestConfig testConfig() {
            return Mockito.mock(TestConfig.class);
        }

    }

}
