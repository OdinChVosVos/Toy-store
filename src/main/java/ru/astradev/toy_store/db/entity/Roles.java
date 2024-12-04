package ru.astradev.toy_store.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_generator")
    @SequenceGenerator(name = "roles_generator", sequenceName = "roles_seq", allocationSize = 1)
    private Long id;

    private String name;
    private String description;


    public Roles(String name, String description) {
        this.name = name;
        this.description = description;
    }
}