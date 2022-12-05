package ru.ds.education.testspringboot.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.ds.education.testspringboot.api.exceptions.HeIsAlreadyThereException;
import ru.ds.education.testspringboot.api.job.BookingJob;
import ru.ds.education.testspringboot.core.mapper.BookedMapper;
import ru.ds.education.testspringboot.core.mapper.CartsMapper;
import ru.ds.education.testspringboot.core.mapper.TrashMapper;
import ru.ds.education.testspringboot.core.model.BookedDto;
import ru.ds.education.testspringboot.core.model.TrashDto;
import ru.ds.education.testspringboot.db.repository.*;

import java.util.List;


@Service
public class CartsService {

    @Autowired
    private CartsRepository cartsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private BookedMapper bookedMapper;

    @Autowired
    private BookedRepository bookedRepository;

    @Autowired
    private TovarRepository tovarRepository;

    @Autowired
    private CartsMapper cartsMapper;

    @Autowired
    private TrashService trashService;

    @Autowired
    private TovarService tovarService;

    @Autowired
    private TrashRepository trashRepository;

    @Autowired
    private TrashMapper trashMapper;

    static BookingJob mSecondThread;

    public List<TrashDto> getAll(Long tgId){
        Long idUser = usersRepository.getByTgID(tgId).getId();

        if (cartsRepository.getLastId(idUser) == null)
            return null;
        Long cartId = cartsRepository.getLastId(idUser);
        return trashMapper.mapAsList(trashService.getByCart(cartId), TrashDto.class);
    }

    public void addToCart(Long tgId, TrashDto tovar){
        Long idUser = usersRepository.getByTgID(tgId).getId();
        if (cartsRepository.getLastId(idUser) == null)
            cartsRepository.add(idUser);
        Long cartId = cartsRepository.getLastId(idUser);
        if (!trashRepository.getTovarFromCart(tovar.getTovar().getId(), cartId).isEmpty())
            throw new HeIsAlreadyThereException();
        trashService.addToCart(tovar, cartId);
    }

    public int countPrice(Long tgId){
        List<TrashDto> goods = getAll(tgId);
        int price = 0;
        for (TrashDto good:goods)
            price += good.getQuantity() * good.getTovar().getCost();
        return price;
    }

    public void clearCart(Long tgId){
        Long cartId = cartsRepository.getLastId(usersRepository.getByTgID(tgId).getId());
        trashRepository.deleteByCart(cartId);
        cartsRepository.deleteById(cartId);
    }

    public void removeFromCart(Long tgId, Long tovarId){
        Long cartId = cartsRepository.getLastId(usersRepository.getByTgID(tgId).getId());
        trashRepository.deleteFromCart(cartId, tovarId);
    }


    public List<BookedDto> buy(Long tgId, Long timeExpire) throws InterruptedException {
        Long idUser = usersRepository.getByTgID(tgId).getId();

        mSecondThread = new BookingJob(idUser, tovarService, timeExpire);
        mSecondThread.start();

        List<TrashDto> cart = getAll(tgId);
        for (TrashDto elem:cart) {
            if (elem.getTovar().getQuantity_in_stock()-elem.getQuantity()>=0){
                tovarRepository.bookGoods(
                        elem.getTovar().getQuantity_in_stock()-elem.getQuantity(),
                        elem.getTovar().getId()
                );
                bookedRepository.putInBooked(elem.getTovar().getId(), elem.getQuantity(), idUser);
            }
        }

        return bookedMapper.mapAsList(bookedRepository.getByUser(idUser), BookedDto.class);
    }



}
