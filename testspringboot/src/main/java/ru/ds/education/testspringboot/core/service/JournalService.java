package ru.ds.education.testspringboot.core.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ds.education.testspringboot.core.mapper.JournalMapper;
import ru.ds.education.testspringboot.core.model.BookedDto;
import ru.ds.education.testspringboot.core.model.JournalDto;
import ru.ds.education.testspringboot.db.repository.JournalRepository;
import ru.ds.education.testspringboot.db.repository.UsersRepository;

import java.util.Date;
import java.util.List;

@Service
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private JournalMapper journalMapper;

    @Autowired
    private UsersRepository usersRepository;

    public void add(String name, String description){
        if(usersRepository.getByName(name) == null) return;

        Long userId = usersRepository.getByName(name).getId();
        journalRepository.add(userId, new Date(), description);
//        return journalMapper.map(journalRepository.getLast(userId), JournalDto.class);
    }
}