package ru.ds.education.testspringboot.core.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.ds.education.testspringboot.core.model.BookedDto;
import ru.ds.education.testspringboot.db.entity.Booked;


@Component
public class BookedMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Booked.class, BookedDto.class)
                .byDefault()
                .register();
    }
}