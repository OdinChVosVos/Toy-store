package ru.astradev.toy_store.core.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.astradev.toy_store.core.mapper.RolesMapper;
import ru.astradev.toy_store.core.model.RolesDto;
import ru.astradev.toy_store.db.repository.RolesRepository;

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
