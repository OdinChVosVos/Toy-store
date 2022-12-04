package ru.ds.education.testspringboot.api.controller;


import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.ds.education.testspringboot.api.job.ParseTask;
import ru.ds.education.testspringboot.core.model.TovarDto;
import ru.ds.education.testspringboot.core.model.UsersDto;
import ru.ds.education.testspringboot.core.service.TovarService;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/api/tovar")
public class TovarController {

    @Autowired
    private TovarService tovarService;

    @Autowired
    private ParseTask parseTask;


    @ApiOperation(
            value = "Добавление товаров"
    )
    @PostMapping
    public TovarDto addTovar(@RequestParam("category") Long category,
                             @RequestParam("name") String name,
                             @RequestParam("cost") int cost,
                             @RequestParam("quantity_in_stock") int quantity,
                             @RequestParam("description") String description,
                             @RequestParam("image") MultipartFile file) throws IOException {
        return tovarService.addTovar(category, name, cost, quantity, description, file);
    }


    @ApiOperation(
            value = "Изменение товаров"
    )
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<List<TovarDto>> updateTovar(@RequestBody List<TovarDto> goods){
        List<TovarDto> result = tovarService.putGoods(goods);
        return ResponseEntity.ok().body(result);
    }

    @ApiOperation(
            value = "Изменение картинки товара"
    )
    @RequestMapping(value = "/update/img/{id}", method = RequestMethod.PUT)
    public void updateImg(@PathVariable Long id, @RequestParam MultipartFile file) throws IOException {
        tovarService.putGood(id, file);
    }


    @ApiOperation(
            value = "Добавление товаров(парсинг)"
    )
    @RequestMapping(value = "/parse", method = RequestMethod.GET)
    public String parseProducts(){
        return parseTask.parseProducts("mashiny");
    }

    @Transactional
    @ApiOperation(
            value = "Получение всех товаров"
    )
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public List<TovarDto> getAll(){
        return tovarService.getAll();
    }

    @Transactional
    @ApiOperation(
            value = "Получение товара по id"
    )
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public TovarDto getTovar(@PathVariable Long id){
        return tovarService.getTovar(id);
    }


    @ApiOperation(
            value = "Получение картинки товара по id"
    )
    @GetMapping("/get/img/{id}")
    public ResponseEntity<?> getTovarImg(@PathVariable Long id){
        return tovarService.downloadImg(id);
    }


    @Transactional
    @ApiOperation(
            value = "Получение товаров в категории"
    )
    @RequestMapping(value = "/get/category/{category}", method = RequestMethod.GET)
    public List<TovarDto> getCategory(@PathVariable int category){
        return tovarService.getCategory(category);
    }


}
