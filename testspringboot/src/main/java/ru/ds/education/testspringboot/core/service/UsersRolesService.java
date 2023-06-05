package ru.ds.education.testspringboot.core.service;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ds.education.testspringboot.api.job.NullProperties;
import ru.ds.education.testspringboot.core.mapper.UsersMapper;
import ru.ds.education.testspringboot.core.mapper.UsersRolesMapper;
import ru.ds.education.testspringboot.core.model.UsersDto;
import ru.ds.education.testspringboot.core.model.UsersRolesDto;
import ru.ds.education.testspringboot.db.entity.Users;
import ru.ds.education.testspringboot.db.repository.UsersRepository;
import ru.ds.education.testspringboot.db.repository.UsersRolesRepository;

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

    public void update(UsersRolesDto userRole){
        Long userId = usersRepository.getByName(userRole.getUser().getName()).getId();
        if (Objects.equals(userRole.getRole().getName(), "")){
            usersRolesRepository.update(userId, 1L);
        } else{
            usersRolesRepository.update(userId, rolesService.getByName(userRole.getRole().getName()).getId());
        }
    }


}
