package ru.astradev.toy_store.core.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.astradev.toy_store.core.model.CartsDto;
import ru.astradev.toy_store.db.entity.Carts;


@Component
public class CartsMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Carts.class, CartsDto.class)
                .byDefault()
                .register();
    }
}
