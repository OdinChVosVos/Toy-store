package ru.astradev.toy_store.core.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArchiveTrashDto {

    @ApiModelProperty("id корзины")
    private Long id;

    @ApiModelProperty("Товар")
    private TovarDto tovar;

    @ApiModelProperty("Количество товара")
    private int quantity;

    @ApiModelProperty("Корзина")
    private ArchiveCartsDto cart;

    public ArchiveTrashDto(TovarDto tovar, int quantity) {
        this.tovar = tovar;
        this.quantity = quantity;
    }
}