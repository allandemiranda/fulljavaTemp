package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.dtos.Person;
import org.example.services.PeopleService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class GettingDataJob implements BatchJob {
    private static final String JOB_NAME = "Getting Data";
    private static final Person DEFAULT_PERSON = new Person("", "", 0, "", "");

    private final PeopleService peopleService;

    @Autowired
    @Inject
    public GettingDataJob(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public void run() {
        //TODO: 3. Getting data from file “people.csv” (5 min) [GettingDataJob]
        //  -	Read the “people.csv” file.
        //  -	Set, to all person that is older 30 years, the COUNTRY value to null (java null). [Mandatory use of "peopleService.setToNullCountryThanThirtyAge()"]
        //  -	Then find the first line that in the column “COUNTRY” is the value “France”, and then print it on screen.
        //  -   If you don't find any row with the expected value, print a default Person object [see DEFAULT_PERSON variable].

        String printOut = peopleService.setToNullCountryThanThirtyAge()

                .collect(Collectors.toList()).get(0)

                .toString();

        log.warn(printOut);
    }

    @Override
    public @NotNull String getJobName() {
        return JOB_NAME;
    }
}
