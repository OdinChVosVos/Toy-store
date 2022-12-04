package ru.ds.education.testspringboot.core.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.ds.education.testspringboot.core.model.TovarDto;
import ru.ds.education.testspringboot.db.entity.Tovar;

@Component
public class TovarMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Tovar.class, TovarDto.class)
                .byDefault()
                .register();
    }
}
