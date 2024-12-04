package ru.astradev.toy_store.core.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsersRolesDto {

    @ApiModelProperty("id связи")
    private Long id;

    @ApiModelProperty("Пользователь")
    private UsersDto user;

    @ApiModelProperty("Роль")
    private RolesDto role;

}