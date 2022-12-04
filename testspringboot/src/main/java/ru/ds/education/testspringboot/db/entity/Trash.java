package ru.ds.education.testspringboot.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Trash")
public class Trash {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trash_generator")
    @SequenceGenerator(name = "trash_generator", sequenceName = "trash_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tovar")
    private Tovar tovar;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cart")
    private Carts cart;


    public Trash(Tovar tovar, int quantity, Carts cart) {
        this.tovar = tovar;
        this.quantity = quantity;
        this.cart = cart;
    }

}