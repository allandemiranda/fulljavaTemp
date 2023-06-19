package org.example.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;

@Controller
public interface BatchJob extends Runnable {
    void run();

    @NotNull String getJobName();
}
