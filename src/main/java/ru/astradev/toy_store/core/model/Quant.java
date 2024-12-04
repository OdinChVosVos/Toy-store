package ru.astradev.toy_store.core.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Quant {
    private Long id;
    private int newQuantity;

    public Quant(Long id, int newQuantity, Long trash) {
        this.id = id;
        this.newQuantity = newQuantity;
    }
}
