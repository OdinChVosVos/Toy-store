package ru.astradev.toy_store.core.mapper;


import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.astradev.toy_store.core.model.ArchiveTrashDto;
import ru.astradev.toy_store.db.entity.ArchiveTrash;

@Component
public class ArchiveTrashMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(ArchiveTrash.class, ArchiveTrashDto.class)
                .byDefault()
                .register();
    }
}