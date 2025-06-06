package ru.otus.hw.matcher;

public interface ResultMatcher {

    boolean isPassed(int rightAnswerCount, int testSize);

}
