package ru.ds.education.testspringboot.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Archive_Carts")
public class ArchiveCarts {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "archive_carts_generator")
    @SequenceGenerator(name = "archive_carts_generator", sequenceName = "archive_carts_seq", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private Users user;

    private String description;


    public ArchiveCarts(Users user, String description) {
        this.user = user;
        this.description = description;
    }
}
