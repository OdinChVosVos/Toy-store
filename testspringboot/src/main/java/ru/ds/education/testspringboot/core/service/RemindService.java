package ru.ds.education.testspringboot.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ds.education.testspringboot.core.mapper.RemindMapper;
import ru.ds.education.testspringboot.core.mapper.TovarMapper;
import ru.ds.education.testspringboot.core.model.RemindDto;
import ru.ds.education.testspringboot.core.model.TovarDto;
import ru.ds.education.testspringboot.db.entity.Remind;
import ru.ds.education.testspringboot.db.entity.Tovar;
import ru.ds.education.testspringboot.db.entity.Trash;
import ru.ds.education.testspringboot.db.repository.RemindRepository;
import ru.ds.education.testspringboot.db.repository.TovarRepository;
import ru.ds.education.testspringboot.db.repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RemindService {

    @Autowired
    private RemindRepository remindrepository;

    @Autowired
    private TovarRepository tovarRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RemindMapper remindMapper;

    @Autowired
    private TovarMapper tovarMapper;

    public void addToRemind(Long tgId, Long idTovar, int quantity){
        double storage = tovarRepository.getById(idTovar).getQuantity_in_stock();
        remindrepository.add(usersRepository.getByTgID(tgId).getId(), idTovar, quantity <= storage, quantity);
    }

    public List<RemindDto> getAll(Long tgId){
        List<Remind> oneUserRemind = remindrepository.getByUser(usersRepository.getByTgID(tgId).getId());
        return remindMapper.mapAsList(oneUserRemind, RemindDto.class);
    }

    public void check(){
        for (Remind good:remindrepository.findAll()) {
            Tovar tovar = tovarRepository.getById(good.getTovar().getId());
            double storage = tovar.getQuantity_in_stock();
            if (good.is_delivered() != (good.getQuantity() <= storage))
                remindrepository.putIsDelivered(!good.is_delivered(), tovar.getId());
        }
    }

    public void removeFromRemind(Long tgId, Long tovarId){
        Long userId = usersRepository.getByTgID(tgId).getId();
        remindrepository.deleteFromRemind(userId, tovarId);
    }

}
