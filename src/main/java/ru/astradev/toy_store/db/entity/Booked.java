package ru.astradev.toy_store.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Booked")
public class Booked {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booked_generator")
    @SequenceGenerator(name = "booked_generator", sequenceName = "booked_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tovar")
    private Tovar tovar;

    private int bookedQuantity;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private Users user;

    public Booked(Tovar tovar, int bookedQuantity) {
        this.tovar = tovar;
        this.bookedQuantity = bookedQuantity;
    }
}
