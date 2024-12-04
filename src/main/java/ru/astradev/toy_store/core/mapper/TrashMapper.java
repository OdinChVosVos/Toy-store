package ru.astradev.toy_store.core.mapper;


import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.astradev.toy_store.core.model.TrashDto;
import ru.astradev.toy_store.db.entity.Trash;

@Component
public class TrashMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Trash.class, TrashDto.class)
                .byDefault()
                .register();
    }
}