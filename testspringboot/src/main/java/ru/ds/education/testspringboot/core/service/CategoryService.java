package ru.ds.education.testspringboot.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ds.education.testspringboot.core.mapper.CartsMapper;
import ru.ds.education.testspringboot.core.mapper.CategoryMapper;
import ru.ds.education.testspringboot.core.model.CategoryDto;
import ru.ds.education.testspringboot.core.model.TovarDto;
import ru.ds.education.testspringboot.core.model.TrashDto;
import ru.ds.education.testspringboot.core.model.UsersDto;
import ru.ds.education.testspringboot.db.repository.CartsRepository;
import ru.ds.education.testspringboot.db.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryService categoryService;

    public CategoryDto add(CategoryDto category){
        categoryRepository.add(category.getName(), category.getDescription());
        return categoryMapper.map(categoryRepository.getByName(category.getName()), CategoryDto.class);
    }

    public List<CategoryDto> getAll(){
        return categoryMapper.mapAsList(categoryRepository.findAll(), CategoryDto.class);
    }

}
