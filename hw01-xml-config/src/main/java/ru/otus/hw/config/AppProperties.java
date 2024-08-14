package ru.otus.hw.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class AppProperties implements TestFileNameProvider {
    private String testFileName;

    public static AppProperties create(String testFileName) {
        Objects.requireNonNull(testFileName, "File name must not be null");
        if (testFileName.isEmpty()) {
            throw new IllegalArgumentException("File name must not be empty");
        }
        return new AppProperties(testFileName);
    }
}
