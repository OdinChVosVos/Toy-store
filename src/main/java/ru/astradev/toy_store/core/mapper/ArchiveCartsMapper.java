package ru.astradev.toy_store.core.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.astradev.toy_store.core.model.ArchiveCartsDto;
import ru.astradev.toy_store.db.entity.ArchiveCarts;


@Component
public class ArchiveCartsMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(ArchiveCarts.class, ArchiveCartsDto.class)
                .byDefault()
                .register();
    }
}
