package org.example.services;

import org.example.dtos.DefaultDto;
import org.example.exceptions.CantProvideServiceException;
import org.example.exceptions.ModelMapperException;
import org.example.models.DefaultModel;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.stream.Stream;

public interface DefaultService<T extends DefaultModel, S extends DefaultDto> {
    default @NotNull S modelToDto(@NotNull T model) {
        throw new ModelMapperException("modelToDto not implemented");
    }

    default @NotNull T dtoToModel(@NotNull S dto) {
        throw new ModelMapperException("dtoToModel not implemented");
    }

    default @NotNull Stream<S> selectAll() {
        throw new CantProvideServiceException("selectAll service not implemented");
    }

    default void saveAll(@NotNull Collection<S> collection) {
        throw new CantProvideServiceException("saveAll service not implemented");
    }
}
