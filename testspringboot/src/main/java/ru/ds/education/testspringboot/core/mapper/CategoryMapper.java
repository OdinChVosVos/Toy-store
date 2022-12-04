package ru.ds.education.testspringboot.core.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.ds.education.testspringboot.core.model.CategoryDto;
import ru.ds.education.testspringboot.db.entity.Category;


@Component
public class CategoryMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Category.class, CategoryDto.class)
                .byDefault()
                .register();
    }
}
