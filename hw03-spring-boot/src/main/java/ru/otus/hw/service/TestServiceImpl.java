package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    public static final String PRINT_ANSWER_FORMAT_TEMPLATE = "%d %s";

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);
        var questionsCount = 1;
        for (var question : questions) {
            ioService.printFormattedLineLocalized("TestService.question.format.template", questionsCount++,
                    question.text());
            var requiredCorrectAnswers = new HashSet<String>();
            var answersCount = 1;
            for (var answer : question.answers()) {
                if (answer.isCorrect()) {
                    requiredCorrectAnswers.add(String.valueOf(answersCount));
                }
                ioService.printFormattedLine(PRINT_ANSWER_FORMAT_TEMPLATE, answersCount++, answer.text());
            }
            var requiredCorrectAnswersCount = requiredCorrectAnswers.size();
            var studentAnswer = getStudentAnswer(requiredCorrectAnswersCount);
            var isStudentAnswerValid = validateStudentAnswer(requiredCorrectAnswers, studentAnswer);
            testResult.applyAnswer(question, isStudentAnswerValid);
        }
        return testResult;
    }

    private String getStudentAnswer(int requiredCorrectAnswersCount) {
        if (requiredCorrectAnswersCount > 1) {
            ioService.printLineLocalized("TestService.write.many.answers");
        } else {
            ioService.printLineLocalized("TestService.write.one.answer");
        }
        return ioService.readString();
    }

    private boolean validateStudentAnswer(Set<String> requiredCorrectAnswers, String studentAnswer) {
        var studentCorrectAnswersCount = Arrays.stream(studentAnswer.split(" "))
                .filter(requiredCorrectAnswers::contains)
                .count();
        return studentCorrectAnswersCount == requiredCorrectAnswers.size();
    }

}
