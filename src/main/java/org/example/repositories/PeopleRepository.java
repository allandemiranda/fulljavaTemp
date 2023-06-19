package org.example.repositories;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.SneakyThrows;
import org.example.exceptions.CantReadFileException;
import org.example.models.PersonModel;
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
public class PeopleRepository implements FileRepository<PersonModel> {

    private @NotNull Collection<PersonModel> getPeopleModels(@NotNull File input) {
        //TODO: 1. Read the file “people.csv” (10 min) [PeopleRepository]
        //  -	Duplicate lines must be removed, keeping only one valid information.
        //  -	Rows should only have 6 columns, rows with different number of columns should be ignored too.
        //  -	Remember that lines with empty text data (columns with empty information) or with the value “null” must also need be ignored.
        //  -	Apply a filter to ignore the line if each column not contains the mandatory data type (information validation).
        //      o	ID, and AGE: are integers
        //      o	NAME, GENDER, DATE, and COUNTRY: are strings

        int skipHeader = 1;
        try {
            FileReader fileReader = new FileReader(input);
            CSVReader csvReader = new CSVReaderBuilder(fileReader).withSkipLines(skipHeader).build();

            // 'csvReader.spliterator()' return an 'Spliterator<String[]>', 'String[]' is each row from the file, and each 'String[n]' is the column index position in 'n'
            // Like, file line '1,Silva,Male,88' is a String[]{"1","Silva","Male","88"}
            return StreamSupport.stream(csvReader.spliterator(), false)
                    .map(strings -> {
                        PersonModel model = new PersonModel();
                        model.setId(Integer.parseInt(strings[0]));
                        model.setName(strings[1]);
                        model.setGender(strings[2]);
                        model.setAge(Integer.parseInt(strings[3]));
                        model.setDate(strings[4]);
                        model.setCountry(strings[5]);
                        return model;
                    })
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new CantReadFileException(e);
        }
    }

    @SneakyThrows
    @Override
    public @NotNull Collection<PersonModel> loadFile(@NotNull Resource input) {
        if (input.isFile() && input.isReadable()) {
            return getPeopleModels(input.getFile());
        } else {
            throw new CantReadFileException("Issue on the file to read");
        }
    }
}
