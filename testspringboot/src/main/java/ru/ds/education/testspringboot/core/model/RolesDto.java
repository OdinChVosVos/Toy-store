package ru.ds.education.testspringboot.core.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RolesDto {

    @ApiModelProperty("id роли")
    private Long id;

    @ApiModelProperty("Название роли")
    private String name;

    @ApiModelProperty("Описание роли")
    private String description;

}
