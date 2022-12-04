package ru.ds.education.testspringboot.core.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.ds.education.testspringboot.core.model.UsersDto;
import ru.ds.education.testspringboot.db.entity.Users;

@Component
public class UsersMapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Users.class, UsersDto.class)
                .byDefault()
                .register();
    }
}
