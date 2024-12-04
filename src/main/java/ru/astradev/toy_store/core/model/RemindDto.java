package ru.astradev.toy_store.core.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RemindDto {

    @ApiModelProperty("id записи в списке")
    private Long id;

    @ApiModelProperty("Покупатель")
    private UsersDto user;

    @ApiModelProperty("Товар")
    private TovarDto tovar;

    @ApiModelProperty("Доставлен ли на склад")
    private boolean is_delivered;

    @ApiModelProperty("Колличество")
    private int quantity;

}