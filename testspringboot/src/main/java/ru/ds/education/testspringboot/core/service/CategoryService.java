package ru.ds.education.testspringboot.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ds.education.testspringboot.core.mapper.CategoryMapper;
import ru.ds.education.testspringboot.core.model.CategoryDto;
import ru.ds.education.testspringboot.db.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    public CategoryDto add(CategoryDto category){
        categoryRepository.add(category.getName(), category.getDescription());
        return categoryMapper.map(categoryRepository.getByName(category.getName()), CategoryDto.class);
    }

    public List<CategoryDto> getAll(){
        return categoryMapper.mapAsList(categoryRepository.findAll(), CategoryDto.class);
    }

    public CategoryDto getByName(String name){
        return categoryMapper.map(categoryRepository.getByName(name), CategoryDto.class);
    }

}
