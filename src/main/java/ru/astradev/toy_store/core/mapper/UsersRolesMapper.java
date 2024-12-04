package ru.astradev.toy_store.core.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.astradev.toy_store.core.model.UsersRolesDto;
import ru.astradev.toy_store.db.entity.UsersRoles;

@Component
public class UsersRolesMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(UsersRoles.class, UsersRolesDto.class)
                .byDefault()
                .register();
    }
}
