package ru.otus.hw.runner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import ru.otus.hw.service.LocalizedIOService;
import ru.otus.hw.service.TestRunnerService;

@Service
@RequiredArgsConstructor
public class ConsoleRunner implements ApplicationRunner {

    private final TestRunnerService testRunnerService;

    private final LocalizedIOService ioService;

    @Override
    public void run(ApplicationArguments args) {
        ioService.printLine("");
        ioService.printLineLocalized("ConsoleRunner.greeting");
        ioService.printLine("");
        testRunnerService.run();
    }

}
