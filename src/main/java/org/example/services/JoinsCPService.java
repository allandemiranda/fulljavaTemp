package org.example.services;

import lombok.SneakyThrows;
import org.example.dtos.JoinCP;
import org.example.models.JoinCPModel;
import org.example.repositories.JoinsCPRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class JoinsCPService implements DefaultService<JoinCPModel, JoinCP> {
    private final JoinsCPRepository repository;

    @Value("classpath:files/output.csv")
    private Resource resourceFile;

    @Autowired
    @Inject
    public JoinsCPService(JoinsCPRepository repository) {
        this.repository = repository;
    }

    @SneakyThrows
    @Override
    public void saveAll(@NotNull Collection<JoinCP> collection) {
        AtomicInteger id = new AtomicInteger(0);
        List<JoinCPModel> joinCPModels = collection.stream().map(this::dtoToModel).map(joinCPModel -> {
            joinCPModel.setId(id.getAndIncrement());
            return joinCPModel;
        }).collect(Collectors.toList());
        repository.saveFile(resourceFile, joinCPModels);
    }

    @Override
    public JoinCPModel dtoToModel(@NotNull JoinCP dto) {
        JoinCPModel model = new JoinCPModel();
        model.setName(dto.name());
        model.setGender(dto.gender());
        model.setAge(dto.age());
        model.setDate(dto.date());
        model.setCountry(dto.country());
        model.setCompany(dto.company());
        model.setEmail(dto.email());
        return model;
    }
}
