package ru.ds.education.testspringboot.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ds.education.testspringboot.api.exceptions.TooMuchException;
import ru.ds.education.testspringboot.core.mapper.TrashMapper;

import ru.ds.education.testspringboot.core.model.TrashDto;

import ru.ds.education.testspringboot.db.entity.Trash;
import ru.ds.education.testspringboot.db.repository.TrashRepository;


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

    public void updateQuantity(Long trashId, int newQuantity){
        TrashDto tovar = trashMapper.map(trashRepository.getById(trashId), TrashDto.class);
        if (tovarService.getTovar(tovar.getTovar().getId()).getQuantity_in_stock() >= tovar.getQuantity())
            trashRepository.updateQuantity(trashId, newQuantity);
        else throw new TooMuchException();

    }

    public void delete(Long id){
        trashRepository.delete(id);
    }

    public List<Trash> getByCart(Long cartId){
        return trashRepository.getByCart(cartId);
    }

}