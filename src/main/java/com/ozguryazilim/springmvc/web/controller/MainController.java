package com.ozguryazilim.springmvc.web.controller;

import com.ozguryazilim.springmvc.model.Animal;
import com.ozguryazilim.springmvc.service.AnimalService;
import com.ozguryazilim.springmvc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final AnimalService animalService;
    private final UserService userService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping(path = {"/","/search"})
    public String home(Model model, String keyword){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isAdmin = userService.isAdmin(user);
        if(!isAdmin){
            model.addAttribute("role", "user");
            if(keyword != null){
                Set<Animal> listByAnimalName = animalService.searchByAnimalName(keyword, user.getUsername());
                model.addAttribute("listAnimals", listByAnimalName);
            }else {
                model.addAttribute("listAnimals", animalService.getAnimalsByUserPrincipal(user.getUsername().toString()));
            }
        }else{
            model.addAttribute("role", "admin");
            if (keyword != null){
                Set<Animal> listByAnimalNameOrOwnerName = animalService.searchByAnimalNameOrOwnerName(keyword);
                model.addAttribute("listAnimals", listByAnimalNameOrOwnerName);
            }else {
                model.addAttribute("listAnimals", animalService.findAllAnimals());
            }
        }
        return "index";
    }
}
