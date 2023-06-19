package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.dtos.Customer;
import org.example.dtos.JoinCP;
import org.example.dtos.Person;
import org.example.services.CustomersService;
import org.example.services.JoinsCPService;
import org.example.services.PeopleService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Controller
public class MergingFilesJob implements BatchJob {
    private static final String JOB_NAME = "Merging Files";
    private static final String EMPTY = "";

    private final CustomersService customersService;
    private final PeopleService peopleService;
    private final JoinsCPService joinsCPService;

    @Autowired
    @Inject
    public MergingFilesJob(CustomersService customersService, PeopleService peopleService, JoinsCPService joinsCPService) {
        this.customersService = customersService;
        this.peopleService = peopleService;
        this.joinsCPService = joinsCPService;
    }

    private @NotNull Collection<JoinCP> merging(@NotNull Collection<Person> people, @NotNull Collection<Customer> customers) {
        //TODO: 4. Merging the files (15 min) [MergingFilesJob]
        //  We need to merge the two tables generated by reading the files.
        //  Return all records from the left table (people.csv) and the corresponding records from the right table (customers.csv), where the key is the column people.COUNTRY = customers.Country.
        //  The final list need contains: NAME, GENDER, AGE, DATE, and COUNTRY columns from people.csv, and Company, and Email columns from customers.csv.

        Customer exCustomer = customers.toArray(new Customer[3])[0];
        return people.stream().map(person -> person.age() > 30 ? joinFacilitator(person, null) : joinFacilitator(person, exCustomer)).collect(Collectors.toList());
    }

    @Override
    public void run() {
        joinsCPService.saveAll(merging(peopleService.selectAll().collect(Collectors.toList()), customersService.selectAll().collect(Collectors.toList())));
    }

    @Override
    public @NotNull String getJobName() {
        return JOB_NAME;
    }

    //! Left table (people) and the corresponding records from the right table (customers)
    private @NotNull JoinCP joinFacilitator(@NotNull Person person, Customer customer) {
        if (Objects.nonNull(customer)) {
            //  Person + Customer
            return new JoinCP(person.name(), person.gender(), person.age(), person.date(), person.country(), customer.company(), customer.email());
        } else {
            //  Person + null
            return new JoinCP(person.name(), person.gender(), person.age(), person.date(), person.country(), EMPTY, EMPTY);
        }
    }
}