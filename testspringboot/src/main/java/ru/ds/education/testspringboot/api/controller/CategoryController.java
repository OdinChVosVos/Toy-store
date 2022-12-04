package ru.ds.education.testspringboot.api.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ds.education.testspringboot.api.job.ParseTask;
import ru.ds.education.testspringboot.core.model.CategoryDto;
import ru.ds.education.testspringboot.core.model.TovarDto;
import ru.ds.education.testspringboot.core.model.UsersDto;
import ru.ds.education.testspringboot.core.service.CategoryService;
import ru.ds.education.testspringboot.core.service.TovarService;

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
