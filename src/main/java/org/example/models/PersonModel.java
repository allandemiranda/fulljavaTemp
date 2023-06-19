package org.example.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PersonModel implements Serializable, DefaultModel {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;
    private String name;
    private String gender;
    private int age;
    private String date;
    private String country;
}
