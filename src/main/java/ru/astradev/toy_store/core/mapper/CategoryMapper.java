package ru.astradev.toy_store.core.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.astradev.toy_store.core.model.CategoryDto;
import ru.astradev.toy_store.db.entity.Category;


@Component
public class CategoryMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Category.class, CategoryDto.class)
                .byDefault()
                .register();
    }
}
