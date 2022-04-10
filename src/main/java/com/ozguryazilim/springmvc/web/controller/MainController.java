package com.ozguryazilim.springmvc.web.controller;

import com.ozguryazilim.springmvc.service.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final AnimalService animalService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/")
    public String home(Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.getAuthorities().contains("ROLE_USER")){
            model.addAttribute("listAnimals", animalService.getAnimalsByUserPrincipal(user.getUsername().toString()));
            return "index";
        }else{
            model.addAttribute("listAnimals", animalService.findAllAnimals());
            return "admin_index";
        }

    }
}
