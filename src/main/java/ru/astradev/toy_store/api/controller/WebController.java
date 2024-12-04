package ru.astradev.toy_store.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.astradev.toy_store.api.job.CardAuth;
import ru.astradev.toy_store.core.model.*;
import ru.astradev.toy_store.core.service.*;
import ru.astradev.toy_store.core.model.*;

import ru.astradev.toy_store.core.service.*;
import ru.astradev.toy_store.db.repository.CartsRepository;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private UsersRolesService usersRolesService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/admin")
    public String admin(Model model){
//        model.addAttribute("users", user.getAll());
        model.addAttribute("users_roles", usersRolesService.getAll());
        model.addAttribute("roles", rolesService.getAll());
        model.addAttribute("user_role", new UsersRolesDto());
        model.addAttribute("user", new UsersDto());
        model.addAttribute("tovarAdd", new TovarAdding());
        model.addAttribute("role", new RolesDto());
        model.addAttribute("tovars", tovarService.getAll());
        model.addAttribute("journals", journalService.getAll());
        model.addAttribute("categories", categoryService.getAll());
        return "admin";
    }

    @RequestMapping("/load")
    public void load(HttpServletResponse response) throws IOException {
        journalService.load(response);
    }

    @GetMapping("/success")
    public String success(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        journalService.add(auth.getName(), "Вход");
        return "redirect:/main";
    }

    @RequestMapping("/failure")
    public String failure(HttpServletRequest request, Model model) {
        journalService.add(request.getParameter("username"), "Неудачная попытка входа");

        model.addAttribute("errorMessage", true);
        model.addAttribute("user", new UsersDto());
        model.addAttribute("signUpErr", false);
        model.addAttribute("signUpSucc", false);
        return "login";
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
        model.addAttribute("errorMessage", false);
        model.addAttribute("signUpErr", false);
        model.addAttribute("signUpSucc", false);
        return "login";
    }


    @RequestMapping("/main")
    public String main(Model model, @ModelAttribute("user") UsersDto user){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = Objects.equals(
                usersRolesService.getRole(usersService.getByName(auth.getName()).getId()).getRole().getName(),
                "ROLE_ADMIN"
        );
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("tovars", tovarService.getAll());
        return "main";
    }

    @Transactional
    @RequestMapping("/cart")
    public String cart(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Long idUser = usersService.getByName(auth.getName()).getId();
        boolean isAdmin = Objects.equals(
                usersRolesService.getRole(usersService.getByName(auth.getName()).getId()).getRole().getName(),
                "ROLE_ADMIN"
        );

        if (cartsRepository.getLastId(idUser) == null || cartsService.getAll(auth.getName()).size() == 0){
            model.addAttribute("status_cart","empty");
            model.addAttribute("isAdmin", isAdmin);
            return "cart";
        }
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("quants", new Quants());
        model.addAttribute("status_cart","not_empty");
        model.addAttribute("goods", cartsService.getAll(auth.getName()));
        model.addAttribute("price", cartsService.countPrice(auth.getName()));
        model.addAttribute("tgId", usersService.getByName(auth.getName()).getId_telegram());
        return "cart";
    }

    @RequestMapping(value = { "/addUser" })
    public String addUser(@ModelAttribute("user") UsersDto user, Model model) {
        try{
            usersService.signUp(user);
            model.addAttribute("user", new UsersDto());
            model.addAttribute("errorMessage", false);
            model.addAttribute("signUpErr", false);
            model.addAttribute("signUpSucc", true);
            return "login";
        }
        catch (RuntimeException e){
            System.out.println(e);
            model.addAttribute("user", new UsersDto());
            model.addAttribute("errorMessage", false);
            model.addAttribute("signUpErr", true);
            model.addAttribute("signUpSucc", false);
            return "login";
        }
    }

    @RequestMapping(value = { "/addTovar" })
    public String addTovar(@ModelAttribute("tovar") TovarAdding tovar) {
        try{
            Long categoryId = categoryService.getByName(tovar.getCategoryName()).getId();
            tovarService.addTovar(
                    categoryId,
                    tovar.getName(),
                    (int) tovar.getCost(),
                    tovar.getQuantity(),
                    tovar.getDescription(),
                    tovar.getFile()
            );
            return "redirect:/admin";
        }
        catch (RuntimeException | IOException e){
            System.out.println(e);
            return "redirect:/admin";
        }
    }

    @RequestMapping(value = { "/updateUser" })
    public String updateUser(@ModelAttribute("user_role") UsersRolesDto userRole) {
        try{
            usersService.updateUser(userRole.getUser());
            usersRolesService.update(userRole);
            return "redirect:/admin";
        }
        catch (RuntimeException e){
            System.out.println(e);
            return "redirect:/admin";
        }
    }

    @RequestMapping(value = { "/updateTovar/{id}" })
    public String updateTovar(@ModelAttribute("tovarAdd") TovarAdding tovar, @PathVariable Long id) {
        try{
            List<TovarDto> list = new ArrayList<>();
            list.add(
                    new TovarDto(
                            id,
                            new CategoryDto(tovar.getCategoryName()),
                            tovar.getName(),
                            tovar.getCost(),
                            tovar.getQuantity(),
                            tovar.getDescription()
                    )
            );
            tovarService.putGoods(list);
            if (!tovar.getFile().isEmpty()){
                tovarService.putGood(id, tovar.getFile());
            }
            if (!Objects.equals(tovar.getCategoryName(), "")){
                tovarService.updateCategory(id, categoryService.getByName(tovar.getCategoryName()).getId());
            }
            return "redirect:/admin";
        }
        catch (RuntimeException | IOException e) {
            System.out.println(e);
            return "redirect:/admin";
        }
    }

    @RequestMapping(value = { "/addUserAdmin" }, method = RequestMethod.GET)
    public String addUserAdmin(@ModelAttribute("user_role") UsersRolesDto userRole) {
        try{
            if (Objects.equals(userRole.getRole().getName(), "")){
                usersService.signUp(userRole.getUser());
            } else{
                usersService.signUpAdmin(userRole.getUser(), rolesService.getByName(userRole.getRole().getName()).getId());
            }
            return "redirect:/admin";
        }
        catch (RuntimeException e){
            System.out.println(e);
            return "redirect:/admin";
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

    @RequestMapping(value = { "/deleteJournal/{id}" }, method = RequestMethod.GET)
    public String deleteJournal(@PathVariable Long id) throws InterruptedException {
        journalService.delete(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = { "/deleteUser/{id}" }, method = RequestMethod.GET)
    public String deleteUser(@PathVariable Long id) throws InterruptedException {
        usersService.delete(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = { "/deleteTovar/{id}" }, method = RequestMethod.GET)
    public String deleteTovar(@PathVariable Long id) throws InterruptedException {
        tovarService.delete(id);
        return "redirect:/admin";
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
