package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("TestServiceImplTest")
public class TestServiceImplTest {

    private final Student student = new Student("Dummy", "Bot");

    private final Question firstQuestion = new Question(
            "What true?",
            List.of(new Answer("A", false), new Answer("B", true))
    );

    private final Question secondQuestion = new Question(
            "What true?",
            List.of(new Answer("A", true), new Answer("B", false))
    );

    private final List<Question> questions = List.of(firstQuestion, secondQuestion);

    @Mock
    private QuestionDao questionDao;

    @Mock
    private IOService ioService;

    @InjectMocks
    private TestServiceImpl testService;

    @BeforeEach
    void setUp() {
        when(questionDao.findAll()).thenReturn(questions);
        String studentAnswer = "2";
        when(ioService.readStringWithPrompt(TestServiceImpl.ASK_TEMPLATE_FOR_ONE_ANSWER)).thenReturn(studentAnswer);
    }

    @Test
    @DisplayName("Should get questions from QuestionDao and pass them to IOService")
    void checkProcessingOfQuestions() {
        var questionTemplate = TestServiceImpl.QUESTION_FORMAT_TEMPLATE;
        var answerTemplate = TestServiceImpl.ANSWER_FORMAT_TEMPLATE;

        testService.executeTestFor(student);

        var expectedQuestionsArgs = new Object[]{1, "What true?"};
        var expectedAnswersArgs = List.of(new Object[]{1, "A"}, new Object[]{2, "B"});

        verify(ioService).printFormattedLine(questionTemplate, expectedQuestionsArgs);
        verify(ioService, times(2))
                .printFormattedLine(answerTemplate, expectedAnswersArgs.get(0));
        verify(ioService, times(2))
                .printFormattedLine(answerTemplate, expectedAnswersArgs.get(1));
    }

    @Test
    @DisplayName("Should get student answers and validate them")
    void checkProcessingOfAnswers() {
        var testResult = testService.executeTestFor(student);

        var actualAnsweredQuestion = testResult.getAnsweredQuestions();
        assertThat(actualAnsweredQuestion)
                .hasSize(questions.size())
                .containsExactlyInAnyOrder(firstQuestion, secondQuestion);

        var actualRightAnswersCount = testResult.getRightAnswersCount();
        var expectedRightAnswersCount = 1;
        assertThat(actualRightAnswersCount).isEqualTo(expectedRightAnswersCount);
    }
}
