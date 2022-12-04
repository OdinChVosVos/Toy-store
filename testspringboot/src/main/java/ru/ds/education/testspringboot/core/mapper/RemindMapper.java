package ru.ds.education.testspringboot.core.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

import ru.ds.education.testspringboot.core.model.RemindDto;

import ru.ds.education.testspringboot.db.entity.Remind;

@Component
public class RemindMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Remind.class, RemindDto.class)
                .byDefault()
                .register();
    }
}
