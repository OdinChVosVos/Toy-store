package ru.astradev.toy_store.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.astradev.toy_store.core.mapper.BookedMapper;
import ru.astradev.toy_store.core.model.BookedDto;
import ru.astradev.toy_store.db.repository.BookedRepository;
import ru.astradev.toy_store.db.repository.UsersRepository;

import java.util.List;


@Service
public class BookedService {

    @Autowired
    private BookedRepository bookedRepository;

    @Autowired
    private BookedMapper bookedMapper;

    @Autowired
    private UsersRepository usersRepository;

    public List<BookedDto> getByUser(Long tgId){
        Long userId = usersRepository.getByTgID(tgId).getId();
        return bookedMapper.mapAsList(bookedRepository.getByUser(userId), BookedDto.class);
    }
}
