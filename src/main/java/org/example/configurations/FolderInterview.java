package org.example.configurations;

import lombok.SneakyThrows;
import org.example.controllers.BatchJob;
import org.example.controllers.GettingDataJob;
import org.example.controllers.MergingFilesJob;
import org.example.controllers.TestJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;

@Configuration
public class FolderInterview implements OrchestratorJobs{

    private final GettingDataJob gettingDataJobController;
    private final MergingFilesJob mergingFilesJobController;
    private final TestJob testJob;

    @Autowired
    @Inject
    public FolderInterview(GettingDataJob gettingDataJobController, MergingFilesJob mergingFilesJobController, TestJob testJob) {
        this.gettingDataJobController = gettingDataJobController;
        this.mergingFilesJobController = mergingFilesJobController;
        this.testJob = testJob;
    }

    @SneakyThrows
    @Override
    @Bean
    public void runNow() {
        Collection<AbstractMap.SimpleEntry<Integer, ? extends BatchJob>> jobs = new ArrayList<>();
//        jobs.add(new AbstractMap.SimpleEntry<Integer, TestJob>(0, testJob));
        jobs.add(new AbstractMap.SimpleEntry<Integer, GettingDataJob>(1, gettingDataJobController));
        jobs.add(new AbstractMap.SimpleEntry<Integer, MergingFilesJob>(2, mergingFilesJobController));
        orchestrator(jobs);
    }
}
