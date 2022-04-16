package com.ozguryazilim.springmvc.service.impl;

import com.ozguryazilim.springmvc.model.Animal;
import com.ozguryazilim.springmvc.model.User;
import com.ozguryazilim.springmvc.repository.AnimalRepository;
import com.ozguryazilim.springmvc.service.AnimalService;
import com.ozguryazilim.springmvc.service.UserService;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@EqualsAndHashCode
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final UserService userService;

    @Override
    @Transactional
    public List<Animal> getAnimalsByUserPrincipal(String username) {
        Long id = userService.getUserIdByUsername(username);
        List<Animal> animals = animalRepository.getAnimalsByOwnerId(id);
        if(animals.isEmpty()){
                return null;
        }
        return animals;
    }

    @Override
    public void saveAnimal(Animal animal) {
        animalRepository.save(animal);
    }

    @Override
    public Animal getAnimalById(Long id) {
        Optional<Animal> optional = animalRepository.findById(id);
        if(optional.isPresent()){
            Animal animal = optional.get();
            return animal;
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

    @Override
    public Set<Animal> searchByAnimalName(String keyword, String username) {
        Long id = userService.getUserIdByUsername(username);
        return animalRepository.findAnimalByName(keyword, id);
    }

    @Override
    public Set<Animal> searchByAnimalNameOrOwnerName(String keyword) {
        return animalRepository.findAnimalByOwnerFirstNameOrName(keyword);
    }
}
