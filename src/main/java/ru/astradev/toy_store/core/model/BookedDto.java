package ru.astradev.toy_store.core.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookedDto {

    @ApiModelProperty("id резерва")
    private Long id;

    @ApiModelProperty("Товар")
    private TovarDto tovar;

    @ApiModelProperty("Количество")
    private int bookedQuantity;

    @ApiModelProperty("Пользователь")
    private UsersDto user;
}