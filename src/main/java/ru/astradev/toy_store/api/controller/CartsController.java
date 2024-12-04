package ru.astradev.toy_store.api.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ru.astradev.toy_store.core.model.TrashDto;
import ru.astradev.toy_store.core.service.CartsService;


import javax.transaction.Transactional;
import java.util.List;


@RestController
@RequestMapping("/api/carts")
public class CartsController {

    @Autowired
    private CartsService cartsService;

    @Transactional
    @ApiOperation(
            value = "Получение всех товаров из корзины"
    )
    @RequestMapping(value = "/get/{tgId}", method = RequestMethod.GET)
    public List<TrashDto> getAll(@PathVariable Long tgId){
        return cartsService.getAll(tgId);
    }


    @ApiOperation(
            value = "Добавление товара в корзину"
    )
    @RequestMapping(value = "/{tgId}", method = RequestMethod.POST)
    public void addToCart(@PathVariable Long tgId, @RequestBody TrashDto tovar){
        cartsService.addToCart(tgId, tovar);
    }




    @ApiOperation(
            value = "Удаление товара из корзины"
    )
    @RequestMapping(value = "/remove/{tgId}", method = RequestMethod.DELETE)
    public void removeFromCart(@PathVariable Long tgId, @RequestParam Long tovarId){
        cartsService.removeFromCart(tgId, tovarId);
    }

}