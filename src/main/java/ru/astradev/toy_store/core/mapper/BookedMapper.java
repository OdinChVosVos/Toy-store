package ru.astradev.toy_store.core.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.astradev.toy_store.core.model.BookedDto;
import ru.astradev.toy_store.db.entity.Booked;


@Component
public class BookedMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Booked.class, BookedDto.class)
                .byDefault()
                .register();
    }
}