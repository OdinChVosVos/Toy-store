package ru.astradev.toy_store.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Remind")
public class Remind {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "remind_generator")
    @SequenceGenerator(name = "remind_generator", sequenceName = "remind_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tovar")
    private Tovar tovar;

    private boolean is_delivered;
    private int quantity;

    public Remind(Users user, Tovar tovar, boolean is_delivered, int quantity) {
        this.user = user;
        this.tovar = tovar;
        this.is_delivered = is_delivered;
        this.quantity = quantity;
    }

    public Remind(Users user, Tovar tovar, boolean is_delivered) {
        this.user = user;
        this.tovar = tovar;
        this.is_delivered = is_delivered;
    }
}
