package ru.astradev.toy_store.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.astradev.toy_store.core.mapper.RemindMapper;
import ru.astradev.toy_store.core.model.RemindDto;

import ru.astradev.toy_store.db.entity.Remind;
import ru.astradev.toy_store.db.entity.Tovar;

import ru.astradev.toy_store.db.repository.RemindRepository;
import ru.astradev.toy_store.db.repository.TovarRepository;
import ru.astradev.toy_store.db.repository.UsersRepository;

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
