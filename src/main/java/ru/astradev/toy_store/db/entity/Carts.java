package ru.astradev.toy_store.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Carts")
public class Carts {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carts_generator")
    @SequenceGenerator(name = "carts_generator", sequenceName = "carts_seq", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private Users user;

    private String description;


    public Carts(Users user, String description) {
        this.user = user;
        this.description = description;
    }
}
