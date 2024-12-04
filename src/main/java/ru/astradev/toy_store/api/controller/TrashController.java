package ru.astradev.toy_store.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.astradev.toy_store.core.service.TrashService;

@RestController
@RequestMapping("/api/trash")
public class TrashController {

    @Autowired
    private TrashService trashService;

}