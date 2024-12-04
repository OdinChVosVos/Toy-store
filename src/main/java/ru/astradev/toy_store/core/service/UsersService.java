package ru.astradev.toy_store.core.service;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.astradev.toy_store.api.job.NullProperties;

import ru.astradev.toy_store.core.model.UsersDto;

import ru.astradev.toy_store.db.entity.Users;
import ru.astradev.toy_store.core.mapper.UsersMapper;
import ru.astradev.toy_store.db.repository.UsersRepository;
import ru.astradev.toy_store.db.repository.UsersRolesRepository;


import java.util.List;
import java.util.Objects;


@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersRolesRepository usersRolesRepository;

    @Autowired
    private UsersMapper usersMapper;


//    @Bean
//    public PasswordEncoder passwordEncoder()
//    {
//        return new BCryptPasswordEncoder();
//    }


    public UsersDto signUp(UsersDto user){
        usersRepository.add(
                user.getId_telegram(), user.getName(),
                user.getPassword(), true,
//                passwordEncoder().encode(user.getPassword()),
                user.getFirstname(), user.getLastname(),
                user.getPhone(), user.getMail(), user.isAgreement()
        );
        UsersDto newUser = usersMapper.map(usersRepository.getByTgID(user.getId_telegram()), UsersDto.class);
        usersRolesRepository.add(newUser.getId(), 1L);

        return newUser;
    }

    public UsersDto signUpAdmin(UsersDto user, Long role_id){
        usersRepository.add(
                user.getId_telegram(), user.getName(),
                user.getPassword(), true,
//                passwordEncoder().encode(user.getPassword()),
                user.getFirstname(), user.getLastname(),
                user.getPhone(), user.getMail(), user.isAgreement()
        );
        UsersDto newUser = usersMapper.map(usersRepository.getByTgID(user.getId_telegram()), UsersDto.class);
        usersRolesRepository.add(newUser.getId(), role_id);

        return newUser;
    }

    public void delete(Long id){
        try{
            if (id == 0) return;
            usersRepository.delete(id);
        }
        catch (RuntimeException e){
            System.out.println(e);
        }
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

    public UsersDto getByName(String name){
        return usersMapper.map(usersRepository.getByName(name), UsersDto.class);
    }

    public boolean checkUsr(UsersDto user){
        return getByName(user.getName())!=null && Objects.equals(getByName(user.getName()).getPassword(), user.getPassword());
    }

}
