package ru.ds.education.testspringboot.core.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.ds.education.testspringboot.core.model.CartsDto;
import ru.ds.education.testspringboot.core.model.UsersRolesDto;
import ru.ds.education.testspringboot.db.entity.Carts;
import ru.ds.education.testspringboot.db.entity.UsersRoles;

@Component
public class UsersRolesMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(UsersRoles.class, UsersRolesDto.class)
                .byDefault()
                .register();
    }
}
