package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class TestJob implements BatchJob {
    private static final String JOB_NAME = "Test Data Job";

    @Override
    public void run() {
        log.warn("This is just a test to a error problem on a job");
        throw new IllegalStateException("Test Illegal State error problem on a job");
    }

    @Override
    public @NotNull String getJobName() {
        return JOB_NAME;
    }
}
