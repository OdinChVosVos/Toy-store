package ru.astradev.toy_store.core.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

import ru.astradev.toy_store.core.model.RemindDto;

import ru.astradev.toy_store.db.entity.Remind;

@Component
public class RemindMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Remind.class, RemindDto.class)
                .byDefault()
                .register();
    }
}
