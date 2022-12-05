package ru.ds.education.testspringboot.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.ds.education.testspringboot.api.job.CardAuth;
import ru.ds.education.testspringboot.core.model.Card;
import ru.ds.education.testspringboot.core.model.UsersDto;

import ru.ds.education.testspringboot.core.service.CartsService;
import ru.ds.education.testspringboot.core.service.TovarService;
import ru.ds.education.testspringboot.core.service.UsersService;


import javax.transaction.Transactional;

@Controller
public class WebController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private TovarService tovarService;

    @Autowired
    private CartsService cartsService;

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
    public String buy(Model model, @PathVariable Long tgId, @Value("${time.expire}") Long timeExpire) throws InterruptedException {
        model.addAttribute("goods", cartsService.buy(tgId, timeExpire));
        model.addAttribute("user", usersService.getByTgId(tgId));
        model.addAttribute("price", cartsService.countPrice(tgId));
        model.addAttribute("card", new Card());
        model.addAttribute("tgId", tgId);
        model.addAttribute("timeExpire", timeExpire);
        return "buy";
    }

    @RequestMapping(value = { "/check/{tgId}" }, method = RequestMethod.GET)
    public String check(@ModelAttribute("card") Card card, @PathVariable Long tgId) throws InterruptedException {
        if (CardAuth.check(card)){
            System.out.println(true);
//            tovarService.putGoods();
//            cartsService.clearCart(tgId);

            return "redirect:/";
        }
        System.out.println(false);
        tovarService.deBook(usersService.getByTgId(tgId).getId());
        return "redirect:/buy/"+tgId;
    }


}
