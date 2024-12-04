package ru.astradev.toy_store.core.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.astradev.toy_store.core.model.TovarDto;
import ru.astradev.toy_store.db.entity.Tovar;

@Component
public class TovarMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Tovar.class, TovarDto.class)
                .byDefault()
                .register();
    }
}
