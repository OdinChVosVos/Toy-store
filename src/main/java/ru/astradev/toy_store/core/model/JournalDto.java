package ru.astradev.toy_store.core.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<String> getAll(){
        List<String> list = new ArrayList<>();
        list.add(id+"");
        list.add(user.getName());
        list.add(datein+"");
        list.add(description);
        return list;
    }

}