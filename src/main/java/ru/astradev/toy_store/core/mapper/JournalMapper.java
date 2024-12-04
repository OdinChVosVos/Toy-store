package ru.astradev.toy_store.core.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.astradev.toy_store.core.model.JournalDto;
import ru.astradev.toy_store.db.entity.Journal;


@Component
public class JournalMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Journal.class, JournalDto.class)
                .byDefault()
                .register();
    }
}
