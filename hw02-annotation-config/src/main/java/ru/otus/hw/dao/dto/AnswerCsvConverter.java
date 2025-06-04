package ru.otus.hw.dao.dto;

import com.opencsv.bean.AbstractCsvConverter;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.exceptions.AnswerFormatException;

public class AnswerCsvConverter extends AbstractCsvConverter {

    public static final String ANSWER_FORMAT = ".*%(true|false)";

    @Override
    public Object convertToRead(String value) {
        if (value.matches(ANSWER_FORMAT)) {
            var valueArr = value.split("%");
            var answerText = valueArr[0];
            var isCorrect = valueArr[1];
            return new Answer(answerText, Boolean.parseBoolean(isCorrect));
        } else {
            throw new AnswerFormatException(
                    "Answer format mast be '%S', but found '%s'".formatted(ANSWER_FORMAT, value)
            );
        }
    }
}
