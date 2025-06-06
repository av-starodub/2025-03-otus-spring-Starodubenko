package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.AnswerFormatException;
import ru.otus.hw.exceptions.QuestionFormatException;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CsvQuestionDaoIntegrationTest {

    private static final String VALID_QUESTIONS_FILE = "valid-questions.csv";

    private static final String INVALID_ANSWER_FORMAT_CSV = "invalid-answer-format.csv";

    private static final String INVALID_QUESTION_FORMAT_CSV = "invalid-question-format.csv";

    private static final String NON_EXISTENT_FILE = "nonexistent-file.csv";

    @Mock
    private TestFileNameProvider fileNameProvider;

    @InjectMocks
    private CsvQuestionDao questionDao;

    @Test
    @DisplayName("Should correctly process valid questions file")
    void checkProcessingOfValidQuestionsFile() {
        when(fileNameProvider.getTestFileName()).thenReturn(VALID_QUESTIONS_FILE);
        var questions = questionDao.findAll();
        var expectedSize = 2;
        assertThat(questions)
                .isNotNull()
                .hasSize(expectedSize);

        var actualQuestion = questions.get(0);
        var expectedQuestion = new Question(
                "What is Java?",
                List.of(new Answer("A programming language", true))
        );
        assertThat(actualQuestion).isEqualTo(expectedQuestion);
    }

    @Test
    @DisplayName(
            "Should throw QuestionReadException with cause AnswerFormatException when found invalid answer format"
    )
    void checkExceptionWhenProcessingInvalidAnswerFormat() {

        when(fileNameProvider.getTestFileName()).thenReturn(INVALID_ANSWER_FORMAT_CSV);

        assertThatThrownBy(() -> questionDao.findAll())
                .isInstanceOf(QuestionReadException.class)
                .hasMessageContaining("Cannot read resource")
                .hasRootCauseInstanceOf(AnswerFormatException.class);
    }

    @Test
    @DisplayName(
            "Should throw QuestionReadException with cause QuestionFormatException when found invalid question format"
    )
    void checkExceptionWhenProcessingInvalidQuestionFormat() {

        when(fileNameProvider.getTestFileName()).thenReturn(INVALID_QUESTION_FORMAT_CSV);

        assertThatThrownBy(() -> questionDao.findAll())
                .isInstanceOf(QuestionReadException.class)
                .hasMessageContaining("Cannot read resource")
                .hasRootCauseInstanceOf(QuestionFormatException.class);
    }

    @Test
    @DisplayName("Should throw QuestionReadException when file does not exist")
    void checkExceptionWhenFileDoesNotExist() {

        when(fileNameProvider.getTestFileName()).thenReturn(NON_EXISTENT_FILE);

        assertThatThrownBy(() -> questionDao.findAll())
                .isInstanceOf(QuestionReadException.class)
                .hasMessageContaining("Cannot read resource");
    }
}
