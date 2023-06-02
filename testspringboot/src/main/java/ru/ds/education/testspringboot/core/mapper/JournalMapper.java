package ru.ds.education.testspringboot.core.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.ds.education.testspringboot.core.model.CartsDto;
import ru.ds.education.testspringboot.core.model.JournalDto;
import ru.ds.education.testspringboot.db.entity.Carts;
import ru.ds.education.testspringboot.db.entity.Journal;


@Component
public class JournalMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Journal.class, JournalDto.class)
                .byDefault()
                .register();
    }
}
