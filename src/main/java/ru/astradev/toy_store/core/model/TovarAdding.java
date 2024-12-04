package ru.astradev.toy_store.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class TovarAdding {
    private Long id;
    private String categoryName;
    private String name;
    private double cost;
    private int quantity;
    private String description;
    private MultipartFile file;

    public TovarAdding(Long id, String categoryName, String name, double cost, int quantity, String description, MultipartFile file) {
        this.id = id;
        this.categoryName = categoryName;
        this.name = name;
        this.cost = cost;
        this.quantity = quantity;
        this.description = description;
        this.file = file;
    }
}