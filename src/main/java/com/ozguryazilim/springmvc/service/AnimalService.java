package com.ozguryazilim.springmvc.service;

import com.ozguryazilim.springmvc.model.Animal;
import com.ozguryazilim.springmvc.model.User;

import java.util.List;
import java.util.Set;

public interface AnimalService {

    List<Animal> getAnimalsByUserPrincipal(String username);
    void saveAnimal(Animal animal);
    Animal getAnimalById(Long id);
    void deleteAnimalById(Long id);
    List<Animal> findAllAnimals();
    Set<Animal> searchByAnimalName(String keyword, String username);
    Set<Animal> searchByAnimalNameOrOwnerName(String keyword);
}
