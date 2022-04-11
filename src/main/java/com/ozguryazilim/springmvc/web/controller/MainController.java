package com.ozguryazilim.springmvc.web.controller;

import com.ozguryazilim.springmvc.service.AnimalService;
import com.ozguryazilim.springmvc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final AnimalService animalService;
    private final UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
    public String home(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isAdmin = userService.isAdmin(user);
        if(!isAdmin){
            model.addAttribute("listAnimals", animalService.getAnimalsByUserPrincipal(user.getUsername().toString()));
            model.addAttribute("role", "user");
            return "index";
        }else{
            model.addAttribute("listAnimals", animalService.findAllAnimals());
            model.addAttribute("role", "admin");
            return "index";
        }

    }
}
