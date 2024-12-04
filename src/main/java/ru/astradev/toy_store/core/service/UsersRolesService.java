package ru.astradev.toy_store.core.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.astradev.toy_store.core.mapper.UsersRolesMapper;
import ru.astradev.toy_store.core.model.UsersRolesDto;
import ru.astradev.toy_store.db.repository.UsersRepository;
import ru.astradev.toy_store.db.repository.UsersRolesRepository;

import java.util.List;
import java.util.Objects;


@Service
public class UsersRolesService {

    @Autowired
    private UsersRolesRepository usersRolesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private UsersRolesMapper usersRolesMapper;


    public List<UsersRolesDto> getAll(){
        return usersRolesMapper.mapAsList(usersRolesRepository.getAll(), UsersRolesDto.class);
    }

    public UsersRolesDto getRole(Long id){
        return usersRolesMapper.map(usersRolesRepository.getRole(id), UsersRolesDto.class);
    }

    public void update(UsersRolesDto userRole){
        Long userId = usersRepository.getByName(userRole.getUser().getName()).getId();
        if (Objects.equals(userRole.getRole().getName(), "")){
            usersRolesRepository.update(userId, 1L);
        } else{
            usersRolesRepository.update(userId, rolesService.getByName(userRole.getRole().getName()).getId());
        }
    }


}
