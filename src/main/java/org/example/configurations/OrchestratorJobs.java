package org.example.configurations;

import org.example.controllers.BatchJob;
import org.example.exceptions.BatchJobException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public interface OrchestratorJobs {
    Logger LOGGER = LoggerFactory.getLogger(OrchestratorJobs.class);

    static boolean errorJobsIsPresent(@NotNull Collection<AbstractMap.SimpleEntry<Integer, ? extends BatchJob>> jobList) {
        Map<Integer, List<? extends BatchJob>> indexing = jobList.stream().collect(Collectors.groupingBy(AbstractMap.SimpleEntry::getKey, Collectors.collectingAndThen(Collectors.toList(), list -> list.stream().map(AbstractMap.SimpleEntry::getValue).collect(Collectors.toList()))));
        return jobList.stream().map(AbstractMap.SimpleEntry::getKey).distinct().sorted().anyMatch(p -> {
            LOGGER.info(String.format("Running level %s", p));
            AtomicBoolean badJob = new AtomicBoolean(false);
            indexing.get(p).parallelStream().forEach(batchJob -> {
                ExecutorService executorService = Executors.newSingleThreadExecutor();
                try {
                    Future<?> future = executorService.submit(batchJob);
                    future.get();
                    LOGGER.info(String.format("Job %s finished OK", batchJob.getJobName()));
                } catch (InterruptedException | ExecutionException e) {
                    LOGGER.error(String.format("Job %s finished BAD", batchJob.getJobName()));
                    LOGGER.error(e.getMessage());
                    e.printStackTrace();
                    badJob.set(true);
                }
                executorService.shutdown();
            });
            return badJob.get();
        });
    }

    default void orchestrator(@NotNull Collection<AbstractMap.SimpleEntry<Integer, ? extends BatchJob>> jobList) throws BatchJobException {
        boolean errorJobsIsPresent = errorJobsIsPresent(jobList);
        if (errorJobsIsPresent) {
            LOGGER.warn("Problem to run jobs, stopping the next level!");
        }
    }

    @Bean
    void runNow();
}
