package ru.geekbrains.shopspringboot.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.shopspringboot.controllers.utils.PageNumbers;
import ru.geekbrains.shopspringboot.entities.User;
import ru.geekbrains.shopspringboot.services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final String DEFAULT_LINES_ON_PAGE = "5";
    private final int MAX_NEIGHBOR_PAGE_NUMBERS = 4;

    private final UserService userService;

    @GetMapping
    public String userList(Model model,
                           @RequestParam(name = "page", defaultValue = "0") Integer page,
                           @RequestParam(name = "pageSize", defaultValue = DEFAULT_LINES_ON_PAGE) Integer pageSize) {

        Page<User> users = userService.findAll(PageRequest.of(page, pageSize));
        model.addAttribute("users", users);
        model.addAttribute("pageNumbers", PageNumbers.get(users.getTotalPages() - 1, users.getNumber(), MAX_NEIGHBOR_PAGE_NUMBERS));
        return "users";
    }

    @GetMapping("add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "user";
    }

    @PostMapping
    public String saveUser(@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "user";
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "", "Passwords are not equals");
            return "user";
        }
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("edit/{id}")
    public String editProduct(Model model, @PathVariable("id") long id) {
        User user = userService.findById(id).orElse(null);
        if (user == null) return "redirect:/users";
        model.addAttribute("user", user);
        return "user";
    }

    @DeleteMapping("delete/{id}")
    public String deleteProduct(Model model, @PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/users";
    }
}
