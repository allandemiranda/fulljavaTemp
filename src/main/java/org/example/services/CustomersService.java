package org.example.services;

import lombok.SneakyThrows;
import org.example.dtos.Customer;
import org.example.models.CustomerModel;
import org.example.repositories.CustomersRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.stream.Stream;

@Service
public class CustomersService implements DefaultService<CustomerModel, Customer> {
    private final CustomersRepository repository;

    @Value("classpath:files/customers.csv")
    private Resource resourceFile;

    @Autowired
    @Inject
    public CustomersService(CustomersRepository repository) {
        this.repository = repository;
    }

    @SneakyThrows
    @Override
    public Stream<Customer> selectAll() {
        return repository.loadFile(resourceFile).stream().map(this::modelToDto);
    }

    @Override
    public Customer modelToDto(@NotNull CustomerModel model) {
        return new Customer(model.getCompany(), model.getCountry(), model.getEmail());
    }
}
