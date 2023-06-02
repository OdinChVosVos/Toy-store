package ru.ds.education.testspringboot.api.controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.ds.education.testspringboot.api.job.CardAuth;
import ru.ds.education.testspringboot.core.model.*;

import ru.ds.education.testspringboot.core.service.*;
import ru.ds.education.testspringboot.db.repository.CartsRepository;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;

@Controller
public class WebController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private JournalService journalService;

    @Autowired
    private TrashService trashService;

    @Autowired
    private TovarService tovarService;

    @Autowired
    private CartsService cartsService;

    @Autowired
    private CartsRepository cartsRepository;


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

    @GetMapping("/success")
    public String success(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        journalService.add(auth.getName(), "Вход");
        return "redirect:/main";
    }

    @RequestMapping("/failure")
    public String failure(HttpServletRequest request, HttpServletResponse response) {
        journalService.add(request.getParameter("username"), "Неудачная попытка входа");
        return "redirect:/login";
    }

    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @PostMapping("/getLogout")
    public String getLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        journalService.add(authentication.getName(), "Выход");
        this.logoutHandler.logout(request, response, authentication);
        return "redirect:/login";
    }

    @RequestMapping("/login")
    public String login(Model model) throws IOException {
        tovarService.photos();
        model.addAttribute("user", new UsersDto());
        return "login";
    }


    @RequestMapping("/main")
    public String main(Model model, @ModelAttribute("user") UsersDto user){
        model.addAttribute("tovars", tovarService.getAll());
        return "main";
    }

    @Transactional
    @RequestMapping("/cart")
    public String cart(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long idUser = usersService.getByName(auth.getName()).getId();

        if (cartsRepository.getLastId(idUser) == null || cartsService.getAll(auth.getName()).size() == 0){
            model.addAttribute("status_cart","empty");
            return "cart";
        }
        model.addAttribute("quants", new Quants());
        model.addAttribute("status_cart","not_empty");
        model.addAttribute("goods", cartsService.getAll(auth.getName()));
        model.addAttribute("price", cartsService.countPrice(auth.getName()));
        model.addAttribute("tgId", usersService.getByName(auth.getName()).getId_telegram());
        return "cart";
    }

    @RequestMapping(value = { "/addUser" }, method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") UsersDto user) {
        try{
            usersService.signUp(user);
            return "redirect:/login";
        }
        catch (RuntimeException e){
            System.out.println(e);
            return "redirect:/login";
        }
    }

    @RequestMapping(value = { "/cancelBuy/{tgId}" }, method = RequestMethod.GET)
    public String cancelBuy(@PathVariable Long tgId) throws InterruptedException {
        tovarService.deBook(usersService.getByTgId(tgId).getId());
        return "redirect:/cart";
    }

    @RequestMapping(value = { "/deleteTrash/{id}" }, method = RequestMethod.GET)
    public String deleteTrash(@PathVariable Long id) throws InterruptedException {
        trashService.delete(id);
        return "redirect:/cart";
    }

    @RequestMapping(value = { "/addToCart/{tovarId}" }, method = RequestMethod.POST)
    public String addToCart(Model model, @PathVariable Long tovarId) {
        try{
            TrashDto newTovar = new TrashDto(tovarService.getTovar(tovarId), 1);
            cartsService.addToCart(newTovar);
            return "redirect:/main";
        }
        catch (RuntimeException e){
            System.out.println(e);
            return "redirect:/main";
        }
    }

    @RequestMapping(value = { "/preBuy/{tgId}" }, method = RequestMethod.POST)
    public String preBuy(Model model, @PathVariable Long tgId, @ModelAttribute("quants") Quants quants) {
        try{
            for (Quant el: quants.getList()) {
                trashService.updateQuantity(el.getId(), el.getNewQuantity());
            }
            return "redirect:/buy/"+tgId;
        }
        catch (RuntimeException e){
            return "redirect:/buy/"+tgId;
        }
    }

    @Transactional
    @RequestMapping(value = "/buy/{tgId}", method = RequestMethod.GET)
    public String buy(Model model, @PathVariable Long tgId,
                      @Value("${time.expire}") Long timeExpire) throws InterruptedException {
        model.addAttribute("goods", cartsService.buy(tgId, timeExpire));
        model.addAttribute("user", usersService.getByTgId(tgId));
        model.addAttribute("price", cartsService.countPrice(tgId));
        model.addAttribute("card", new Card());
        model.addAttribute("tgId", tgId);
        model.addAttribute("timeExpire", timeExpire);
        model.addAttribute("status", "notexpired");
        model.addAttribute("statusexc", "notexpired");
        model.addAttribute("pop_up_exp", "pop_up");
        model.addAttribute("pop_up_exc", "not_pop_up");
        model.addAttribute("pop_up_fail", "not_pop_up");
        return "buy";
    }

    @RequestMapping(value = { "/check/{tgId}" }, method = RequestMethod.GET)
    public String check(Model model, @ModelAttribute("card") Card card, @PathVariable Long tgId) throws InterruptedException {
        if (cartsService.getmSecondThread() != null)
            cartsService.getmSecondThread().stop();
        if (CardAuth.check(card)){
            System.out.println(true);

            cartsService.clearCart(tgId);
            tovarService.deBook(usersService.getByTgId(tgId).getId());

            model.addAttribute("status", "notexpired");
            model.addAttribute("statusexc", "expired");
            model.addAttribute("tgId", tgId);
            model.addAttribute("user", usersService.getByTgId(tgId));
            model.addAttribute("timeExpire", 0);
            model.addAttribute("pop_up_exp", "not_pop_up");
            model.addAttribute("pop_up_fail", "not_pop_up");
            model.addAttribute("pop_up_exc", "pop_up");

            return "buy";
        }
        System.out.println(false);

        model.addAttribute("status", "expired");
        model.addAttribute("statusexc", "notexpired");
        model.addAttribute("tgId", tgId);
        model.addAttribute("user", usersService.getByTgId(tgId));
        model.addAttribute("timeExpire", 0);
        model.addAttribute("pop_up_exp", "not_pop_up");
        model.addAttribute("pop_up_exc", "not_pop_up");
        model.addAttribute("pop_up_fail", "pop_up");

        tovarService.deBook(usersService.getByTgId(tgId).getId());
        return "buy";
    }


}
