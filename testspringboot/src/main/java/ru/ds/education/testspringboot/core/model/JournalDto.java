package ru.ds.education.testspringboot.core.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class JournalDto {

    @ApiModelProperty("id записи")
    private Long id;

    @ApiModelProperty("Пользователь")
    private UsersDto user;

    @ApiModelProperty("Дата входа")
    private Date datein;

    @ApiModelProperty("Описание")
    private String description;

}