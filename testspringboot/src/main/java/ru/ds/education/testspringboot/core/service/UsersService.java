package ru.ds.education.testspringboot.core.service;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ds.education.testspringboot.api.job.NullProperties;
import ru.ds.education.testspringboot.core.model.TovarDto;
import ru.ds.education.testspringboot.core.model.UsersDto;
import ru.ds.education.testspringboot.db.entity.Tovar;
import ru.ds.education.testspringboot.db.entity.Users;
import ru.ds.education.testspringboot.core.mapper.UsersMapper;
import ru.ds.education.testspringboot.db.repository.UsersRepository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersMapper usersMapper;


    public UsersDto signUp(UsersDto user){
        usersRepository.add(
                user.getId_telegram(), user.getName(),
                user.getFirstname(), user.getLastname(),
                user.getPhone(), user.getMail(), user.isAgreement()
        );
        return usersMapper.map(usersRepository.getByTgID(user.getId_telegram()), UsersDto.class);
    }

    public UsersDto updateUser(UsersDto user){
        Users existingUser = usersRepository.getByTgID(user.getId_telegram());
        BeanUtils.copyProperties(user, existingUser, NullProperties.getNullPropertyNames(user));
        return usersMapper.map(usersRepository.saveAndFlush(existingUser), UsersDto.class);
    }

    public List<UsersDto> getAll(){
        return usersMapper.mapAsList(usersRepository.findAll(), UsersDto.class);
    }

    public UsersDto getByTgId(Long tgId){
        return usersMapper.map(usersRepository.getByTgID(tgId), UsersDto.class);
    }

}
