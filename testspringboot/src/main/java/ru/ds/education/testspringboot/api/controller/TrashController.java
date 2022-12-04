package ru.ds.education.testspringboot.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ds.education.testspringboot.core.service.TrashService;

@RestController
@RequestMapping("/api/trash")
public class TrashController {

    @Autowired
    private TrashService trashService;

}