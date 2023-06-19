package org.example.repositories;

import org.example.exceptions.CantReadFileException;
import org.example.exceptions.CantWriteFileException;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;

@Repository
public interface FileRepository<T> {
    default @NotNull Collection<T> loadFile(@NotNull Resource input) {
        throw new CantReadFileException("Read method not implemented");
    }

    @Transactional
    default void saveFile(@NotNull Resource output, @NotNull Collection<T> lines) {
        throw new CantWriteFileException("Write method not implemented");
    }
}
