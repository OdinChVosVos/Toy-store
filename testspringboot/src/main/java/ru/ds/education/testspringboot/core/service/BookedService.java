package ru.ds.education.testspringboot.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ds.education.testspringboot.core.mapper.BookedMapper;
import ru.ds.education.testspringboot.core.model.BookedDto;
import ru.ds.education.testspringboot.db.repository.BookedRepository;
import ru.ds.education.testspringboot.db.repository.UsersRepository;

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
