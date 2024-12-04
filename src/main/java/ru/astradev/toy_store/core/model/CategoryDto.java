package ru.astradev.toy_store.core.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

    @ApiModelProperty("id категории")
    private Long id;

    @ApiModelProperty("Название категории")
    private String name;

    @ApiModelProperty("Описание товара")
    private String description;

    public CategoryDto(String name) {
        this.name = name;
    }
}
