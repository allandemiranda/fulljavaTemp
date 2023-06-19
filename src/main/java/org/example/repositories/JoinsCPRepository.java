package org.example.repositories;

import com.opencsv.CSVWriter;
import lombok.SneakyThrows;
import org.example.exceptions.CantWriteFileException;
import org.example.models.JoinCPModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

@Repository
public class JoinsCPRepository implements FileRepository<JoinCPModel> {

    private static final String[] HEADER = {"#ID", "NAME", "GENDER", "AGE", "DATE", "COUNTRY", "Company", "Email"};

    private void writeLines(@NotNull File output, @NotNull Collection<JoinCPModel> lines) {
        //TODO: 5. Writing the merging of tables (3 min) [JoinsCPRepository]
        //  -   Now we need to save the new table in an output file “output.csv”.
        //  -	If the output file contains data, it must be deleted, and new data added.
        //  -	Don't forget the header (use the name COUNTRY for the merge key column). [You can use HEADER variable]

        try {
            FileWriter fileWriter = new FileWriter(output, true);
            CSVWriter csvWriter = new CSVWriter(fileWriter);

            csvWriter.writeNext(HEADER);

            lines.stream()
                    .map(joinCPModel -> new String[]{
                            String.valueOf(joinCPModel.getId()),
                            joinCPModel.getName(),
                            joinCPModel.getGender(),
                            String.valueOf(joinCPModel.getAge()),
                            joinCPModel.getDate(),
                            joinCPModel.getCountry(),
                            joinCPModel.getCompany(),
                            joinCPModel.getEmail()}
                    )
                    .forEach(csvWriter::writeNext);
        } catch (IOException e) {
            throw new CantWriteFileException(e);
        }
    }

    @SneakyThrows
    @Transactional
    @Override
    public void saveFile(@NotNull Resource output, @NotNull Collection<JoinCPModel> lines) {
        if (output.exists()) {
            writeLines(output.getFile(), lines);
        } else {
            throw new CantWriteFileException("File not found");
        }
    }
}
