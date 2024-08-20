package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {
    public static final String QUESTION_FORMAT_TEMPLATE = "Question %d. %s";

    public static final String ANSWER_FORMAT_TEMPLATE = "%d %s";

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);
        var questionsCount = 1;
        for (var question : questions) {
            ioService.printFormattedLine(QUESTION_FORMAT_TEMPLATE, questionsCount++, question.text());
            var requiredCorrectAnswers = new HashSet<String>();
            var answersCount = 1;
            for (var answer : question.answers()) {
                if (answer.isCorrect()) {
                    requiredCorrectAnswers.add(String.valueOf(answersCount));
                }
                ioService.printFormattedLine(ANSWER_FORMAT_TEMPLATE, answersCount++, answer.text());
            }
            var requiredCorrectAnswersCount = requiredCorrectAnswers.size();
            var studentAnswer = getStudentAnswer(requiredCorrectAnswersCount);
            boolean isStudentAnswerValid = validateStudentAnswer(requiredCorrectAnswers, studentAnswer);
            testResult.applyAnswer(question, isStudentAnswerValid);
        }
        return testResult;
    }

    private String getStudentAnswer(int requiredCorrectAnswersCount) {
        var studentAnswer = "";
        if (requiredCorrectAnswersCount > 1) {
            studentAnswer = ioService.readStringWithPrompt("Write answers down separated by a space and press enter");
        } else {
            studentAnswer = ioService.readStringWithPrompt("Write answer and press enter");
        }
        return studentAnswer;
    }

    private boolean validateStudentAnswer(Set<String> requiredCorrectAnswers, String studentAnswer) {
        var studentCorrectAnswersCount = Arrays.stream(studentAnswer.split(" "))
                .filter(requiredCorrectAnswers::contains)
                .count();
        return studentCorrectAnswersCount == requiredCorrectAnswers.size();
    }
}
