package ru.astradev.toy_store.core.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartsDto {

    @ApiModelProperty("id корзины")
    private Long id;

    @ApiModelProperty("Покупатель")
    private UsersDto user;

    @ApiModelProperty("Описание")
    private String description;

}