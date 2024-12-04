package ru.astradev.toy_store.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Journal")
public class Journal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "journal_generator")
    @SequenceGenerator(name = "journal_generator", sequenceName = "journal_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users user;

    private Date datein;
    private String description;

    public Journal(Users user, Date dateIn, String description) {
        this.user = user;
        this.datein = dateIn;
        this.description = description;
    }
}