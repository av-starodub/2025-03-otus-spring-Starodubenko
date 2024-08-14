package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    public static final String QUESTION_FORMAT_TEMPLATE = "Question %d. %s";

    public static final String ANSWER_FORMAT_TEMPLATE = "%d %s";

    private final IOService ioService;

    private final QuestionDao dao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = dao.findAll();
        printQuestions(questions);
    }

    private void printQuestions(List<Question> questions) {
        final int[] questionCount = {1};
        questions.forEach(question -> {
            ioService.printFormattedLine(QUESTION_FORMAT_TEMPLATE, questionCount[0]++, question.text());
            final int[] answerCount = {1};
            question.answers().stream()
                    .map(Answer::text)
                    .forEach(answer -> ioService.printFormattedLine(ANSWER_FORMAT_TEMPLATE, answerCount[0]++, answer));
            ioService.printLine("");
        });
    }
}
