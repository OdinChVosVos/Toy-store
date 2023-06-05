package ru.ds.education.testspringboot.core.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ds.education.testspringboot.core.mapper.RolesMapper;
import ru.ds.education.testspringboot.core.mapper.UsersRolesMapper;
import ru.ds.education.testspringboot.core.model.RolesDto;
import ru.ds.education.testspringboot.core.model.UsersRolesDto;
import ru.ds.education.testspringboot.db.repository.RolesRepository;
import ru.ds.education.testspringboot.db.repository.UsersRolesRepository;

import java.util.List;


@Service
public class RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private RolesMapper rolesMapper;


    public List<RolesDto> getAll(){
        return rolesMapper.mapAsList(rolesRepository.getAll(), RolesDto.class);
    }

    public RolesDto getByName(String name){
        return rolesMapper.map(rolesRepository.getByName(name), RolesDto.class);
    }


}
