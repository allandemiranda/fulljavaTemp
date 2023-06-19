package org.example.services;

import lombok.SneakyThrows;
import org.example.dtos.Person;
import org.example.models.PersonModel;
import org.example.repositories.PeopleRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.stream.Stream;

@Service
public class PeopleService implements DefaultService<PersonModel, Person> {
    private final PeopleRepository repository;

    @Value("classpath:files/people.csv")
    private Resource resourceFile;

    @Autowired
    @Inject
    public PeopleService(PeopleRepository repository) {
        this.repository = repository;
    }

    @Override
    @SneakyThrows
    public Stream<Person> selectAll() {
        return repository.loadFile(resourceFile).stream().map(this::modelToDto);
    }

    @SneakyThrows
    public Stream<Person> setToNullCountryThanThirtyAge() {
        return repository.loadFile(resourceFile).stream().map(person -> {
            if (person.getAge() > 30) {
                person.setCountry(null);
            }
            return person;
        }).map(this::modelToDto);
    }

    @Override
    public Person modelToDto(@NotNull PersonModel model) {
        return new Person(model.getName(), model.getGender(), model.getAge(), model.getDate(), model.getCountry());
    }
}
