package ru.ds.education.testspringboot.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Tovar")
@Builder
public class Tovar {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tovar_generator")
    @SequenceGenerator(name = "tovar_generator", sequenceName = "tovar_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_category")
    private Category category;

    private String name;
    private double cost;
    private int quantity_in_stock;
    private String description;

    @Lob
    @Column(name = "photo")
    private byte[] photo;


    public Tovar(Category category, String name, double cost, int quantity_in_stock, String description, byte[] photo) {
        this.category = category;
        this.name = name;
        this.cost = cost;
        this.quantity_in_stock = quantity_in_stock;
        this.description = description;
        this.photo = photo;
    }

    public Tovar(Long id, Category category, String name, double cost, int quantity_in_stock, String description, byte[] photo) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.cost = cost;
        this.quantity_in_stock = quantity_in_stock;
        this.description = description;
        this.photo = photo;
    }
}
