package org.example.repositories;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.SneakyThrows;
import org.example.exceptions.CantReadFileException;
import org.example.models.CustomerModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class CustomersRepository implements FileRepository<CustomerModel> {

    private @NotNull Collection<CustomerModel> getCustomerModels(@NotNull File input) {
        //TODO: 2. Read the file “customers.csv” (3 min) [CustomersRepository]
        //  In this case, all data types for every column in that file have already been validated.
        //  You'll only need to extract the Index (id), Company, Country, and Email columns.
        //  Don’t need any data validation.

        int skipHeader = 1;
        try {
            FileReader fileReader = new FileReader(input);
            CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(skipHeader).build();

            return StreamSupport.stream(csvReader.spliterator(), false).map(strings -> {
                CustomerModel model = new CustomerModel();
                model.setId(Integer.parseInt(strings[0]));
                model.setCountry(strings[4]);
                model.setCompany(strings[6]);
                model.setEmail(strings[9]);
                return model;
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new CantReadFileException(e);
        }
    }

    @SneakyThrows
    @Override
    public @NotNull Collection<CustomerModel> loadFile(@NotNull Resource input) {
        if (input.isFile() && input.isReadable()) {
            return getCustomerModels(input.getFile());
        } else {
            throw new CantReadFileException("Issue on the file to read");
        }

    }
}
