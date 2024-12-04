package ru.astradev.toy_store.api.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.astradev.toy_store.core.model.RemindDto;
import ru.astradev.toy_store.core.model.TovarDto;
import ru.astradev.toy_store.core.service.RemindService;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/remind")
public class RemindController {

    @Autowired
    private RemindService remindService;


    @ApiOperation(
            value = "Добавление товара в список желаемого"
    )
    @RequestMapping(value = "/{tgId}", method = RequestMethod.POST)
    public void addToRemind(@PathVariable Long tgId,
                            @RequestBody TovarDto tovar,
                            @RequestParam int quantity)
    {
        remindService.addToRemind(tgId, tovar.getId(), quantity);
    }

    @Transactional
    @ApiOperation(
            value = "Получение всех товаров из списка желаемого"
    )
    @RequestMapping(value = "/get/{tgId}", method = RequestMethod.GET)
    public List<RemindDto> getAll(@PathVariable Long tgId){
        return remindService.getAll(tgId);
    }


    @ApiOperation(
            value = "Удаление товара из remind"
    )
    @RequestMapping(value = "/remove/{tgId}", method = RequestMethod.DELETE)
    public void removeFromRemind(@PathVariable Long tgId, @RequestParam Long tovarId){
        remindService.removeFromRemind(tgId, tovarId);
    }
}