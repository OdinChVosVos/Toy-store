package ru.astradev.toy_store.api.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.astradev.toy_store.core.model.CategoryDto;

import ru.astradev.toy_store.core.service.CategoryService;


import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @ApiOperation(
            value = "Добавление категории"
    )
    @PostMapping
    public CategoryDto add(@RequestBody CategoryDto category){
        return categoryService.add(category);
    }


    @ApiOperation(
            value = "Получение всех категорий"
    )
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<CategoryDto> getAll(){
        return categoryService.getAll();
    }

}
