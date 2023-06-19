package org.example.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Accessors(fluent = true)
@Getter
@ToString
public class JoinCP implements DefaultDto {
    @NotNull
    private final String name;
    @NotNull
    private final String gender;
    @NotNull
    private final int age;
    @NotNull
    private final String date;
    @NotNull
    private final String country;
    @NotNull
    private final String company;
    @NotNull
    private final String email;
}
