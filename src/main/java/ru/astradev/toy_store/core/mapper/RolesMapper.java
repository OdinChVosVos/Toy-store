package ru.astradev.toy_store.core.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.astradev.toy_store.core.model.RolesDto;
import ru.astradev.toy_store.db.entity.Roles;

@Component
public class RolesMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Roles.class, RolesDto.class)
                .byDefault()
                .register();
    }
}

