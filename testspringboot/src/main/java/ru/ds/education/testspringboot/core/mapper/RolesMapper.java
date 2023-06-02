//package ru.ds.education.testspringboot.core.mapper;
//
//import ma.glasnost.orika.MapperFactory;
//import ma.glasnost.orika.impl.ConfigurableMapper;
//import org.springframework.stereotype.Component;
//import ru.ds.education.testspringboot.core.model.CategoryDto;
//import ru.ds.education.testspringboot.core.model.RolesDto;
//import ru.ds.education.testspringboot.db.entity.Category;
//import ru.ds.education.testspringboot.db.entity.Roles;
//
//@Component
//public class RolesMapper extends ConfigurableMapper {
//
//    @Override
//    protected void configure(MapperFactory factory) {
//        factory.classMap(Roles.class, RolesDto.class)
//                .byDefault()
//                .register();
//    }
//}
//
