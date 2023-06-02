package ru.ds.education.testspringboot.core.mapper;


import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.ds.education.testspringboot.core.model.ArchiveTrashDto;
import ru.ds.education.testspringboot.core.model.TrashDto;
import ru.ds.education.testspringboot.db.entity.ArchiveTrash;
import ru.ds.education.testspringboot.db.entity.Trash;

@Component
public class ArchiveTrashMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(ArchiveTrash.class, ArchiveTrashDto.class)
                .byDefault()
                .register();
    }
}