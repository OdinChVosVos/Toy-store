package ru.ds.education.testspringboot.core.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.ds.education.testspringboot.core.model.ArchiveCartsDto;
import ru.ds.education.testspringboot.core.model.CartsDto;
import ru.ds.education.testspringboot.db.entity.ArchiveCarts;
import ru.ds.education.testspringboot.db.entity.Carts;


@Component
public class ArchiveCartsMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(ArchiveCarts.class, ArchiveCartsDto.class)
                .byDefault()
                .register();
    }
}
