package ru.astradev.toy_store.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.astradev.toy_store.api.exceptions.TooMuchException;
import ru.astradev.toy_store.core.mapper.TrashMapper;

import ru.astradev.toy_store.core.model.TrashDto;

import ru.astradev.toy_store.db.entity.Trash;
import ru.astradev.toy_store.db.repository.TrashRepository;


import javax.transaction.Transactional;
import java.util.List;

@Service
public class TrashService {

    @Autowired
    private TrashRepository trashRepository;

    @Autowired
    private TovarService tovarService;

    @Autowired
    private TrashMapper trashMapper;

    public void addToCart(TrashDto tovar, Long cartId){
        if (tovarService.getTovar(tovar.getTovar().getId()).getQuantity_in_stock() >= tovar.getQuantity())
            trashRepository.add(tovar.getTovar().getId(), tovar.getQuantity(), cartId);
        else throw new TooMuchException();
    }

    @Transactional
    public void updateQuantity(Long trashId, int newQuantity){
        TrashDto tovar = trashMapper.map(trashRepository.getById(trashId), TrashDto.class);
        if (tovarService.getTovar(tovar.getTovar().getId()).getQuantity_in_stock() >= newQuantity) {
            trashRepository.updateQuantity(trashId, newQuantity);
        }
        else throw new TooMuchException();
    }

    public void delete(Long id){
        trashRepository.delete(id);
    }

    public void deleteByTovar(Long id){
        trashRepository.deleteByTovar(id);
    }

    public List<Trash> getByCart(Long cartId){
        return trashRepository.getByCart(cartId);
    }

}