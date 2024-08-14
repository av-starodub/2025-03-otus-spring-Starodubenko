package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao dao;

    @Override
    public void executeTest() {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = dao.findAll();
        questions.forEach(question -> ioService.printFormattedLine(question.text(), getListAnswers(question)));
    }

    private List<String> getListAnswers(Question question) {
        return question.answers().stream()
                .map(Answer::text)
                .toList();
    }
}
