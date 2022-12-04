package ru.ds.education.testspringboot.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.ds.education.testspringboot.api.job.CardAuth;
import ru.ds.education.testspringboot.core.mapper.UsersMapper;
import ru.ds.education.testspringboot.core.model.Card;
import ru.ds.education.testspringboot.core.model.UsersDto;
import ru.ds.education.testspringboot.core.service.BookedService;
import ru.ds.education.testspringboot.core.service.CartsService;
import ru.ds.education.testspringboot.core.service.TovarService;
import ru.ds.education.testspringboot.core.service.UsersService;
import ru.ds.education.testspringboot.db.entity.Users;
import ru.ds.education.testspringboot.db.repository.BookedRepository;

import javax.transaction.Transactional;

@Controller
public class WebController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private TovarService tovarService;

    @Autowired
    private CartsService cartsService;

    @Autowired
    private BookedService bookedService;

    @Autowired
    private UsersMapper usersMapper;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/admin")
    public String admin(Model model){
        model.addAttribute("users", usersService.getAll());
        model.addAttribute("user", new UsersDto());
        model.addAttribute("tovars", tovarService.getAll());
        return "admin";
    }

    @RequestMapping(value = { "/addUser" }, method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") UsersDto user) {
        usersService.signUp(user);
        return "redirect:/admin";
    }

    @RequestMapping("/overTime/{tgId}")
    public String overTime(Model model, @PathVariable Long tgId) {
        model.addAttribute("tgId", tgId);
        return "overTime";
    }

    @Transactional
    @RequestMapping("/buy/{tgId}")
    public String buy(Model model, @PathVariable Long tgId) throws InterruptedException {
        model.addAttribute("goods", cartsService.buy(tgId));
//        model.addAttribute("goods", cartsService.getAll(tgId));
        model.addAttribute("user", usersService.getByTgId(tgId));
        model.addAttribute("price", cartsService.countPrice(tgId));
        model.addAttribute("card", new Card());
        model.addAttribute("tgId", tgId);
        return "buy";
    }

    @RequestMapping(value = { "/check/{tgId}" }, method = RequestMethod.GET)
    public String check(@ModelAttribute("card") Card card, @PathVariable Long tgId) {
        if (bookedService.getByUser(tgId).isEmpty())
            return "redirect:/overTime/" + tgId;
        if (CardAuth.check(card)){
            System.out.println(true);
//            tovarService.putGoods();
//            cartsService.clearCart(tgId);

            return "redirect:/";
        }
        System.out.println(false);
        return "redirect:/buy/"+tgId;
    }


}
