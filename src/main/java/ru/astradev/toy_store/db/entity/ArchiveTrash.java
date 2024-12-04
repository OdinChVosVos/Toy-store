package ru.astradev.toy_store.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Archive_Trash")
public class ArchiveTrash {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "archive_trash_generator")
    @SequenceGenerator(name = "archive_trash_generator", sequenceName = "archive_trash_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tovar")
    private Tovar tovar;

    private int quantity;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_cart")
    private ArchiveCarts cart;


    public ArchiveTrash(Tovar tovar, int quantity, ArchiveCarts cart) {
        this.tovar = tovar;
        this.quantity = quantity;
        this.cart = cart;
    }

}