package ru.astradev.toy_store.core.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.astradev.toy_store.core.model.UsersDto;
import ru.astradev.toy_store.db.entity.Users;

@Component
public class UsersMapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Users.class, UsersDto.class)
                .byDefault()
                .register();
    }
}
