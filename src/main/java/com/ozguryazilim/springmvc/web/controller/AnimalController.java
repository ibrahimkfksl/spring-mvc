package com.ozguryazilim.springmvc.web.controller;

import com.ozguryazilim.springmvc.model.Animal;
import com.ozguryazilim.springmvc.service.AnimalService;
import com.ozguryazilim.springmvc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private final UserService userService;

    @GetMapping("/showNewAnimalForm")
    public String showNewAnimalForm(Model model){
        Animal animal = new Animal();
        model.addAttribute("animal", animal);
        return "new_animal";
    }

    @PostMapping("/saveAnimal")
    public String saveAnimal(@ModelAttribute("animal") Animal animal){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        animal.setOwner(userService.getUserByUsername(user.getUsername().toString()));
        animalService.saveAnimal(animal);
        return "redirect:/";
    }

    @GetMapping("/showUpdateAnimalForm/{id}")
    public String showUpdateAnimalForm(@PathVariable(value = "id") long id, Model model){
        Animal animal = animalService.getAnimalById(id);
        model.addAttribute("animal", animal);
        return "update_animal";
    }

    @GetMapping("/deleteAnimal/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String deleteAnimal(@PathVariable(value = "id") long id){
        animalService.deleteAnimalById(id);
        return "redirect:/";
    }

}
