package com.ozguryazilim.springmvc.service.impl;

import com.ozguryazilim.springmvc.model.Animal;
import com.ozguryazilim.springmvc.model.User;
import com.ozguryazilim.springmvc.repository.AnimalRepository;
import com.ozguryazilim.springmvc.service.AnimalService;
import com.ozguryazilim.springmvc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final UserService userService;

    @Override
    public List<Animal> getAnimalsByUserPrincipal(String username) {

        Long id = userService.getUserIdByUsername(username);
        List<Animal> animals = animalRepository.getAnimalsByOwnerId(id);

        if(animals.isEmpty()){
            return null;
        }else{
            return animals;
        }

    }

    @Override
    public void saveAnimal(Animal animal) {
        animalRepository.save(animal);
    }

    @Override
    public Animal getAnimalById(Long id) {
       Optional<Animal> optional = animalRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        else{
            throw new RuntimeException("Animal Not Found For Id::"+id);
        }

    }

    @Override
    public void deleteAnimalById(Long id) {
        animalRepository.deleteById(id);
    }

    @Override
    public List<Animal> findAllAnimals() {
        return animalRepository.findAll();
    }
}
