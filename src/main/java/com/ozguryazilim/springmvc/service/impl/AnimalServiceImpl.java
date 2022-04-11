package com.ozguryazilim.springmvc.service.impl;

import com.ozguryazilim.springmvc.model.Animal;
import com.ozguryazilim.springmvc.model.User;
import com.ozguryazilim.springmvc.repository.AnimalRepository;
import com.ozguryazilim.springmvc.service.AnimalService;
import com.ozguryazilim.springmvc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final UserService userService;
    private final RedisTemplate<String, Animal> redisTemplate;

    @Override
    public List<Animal> getAnimalsByUserPrincipal(String username) {
        Long id = userService.getUserIdByUsername(username);
        List<Animal> cacheAnimal = redisTemplate.opsForList().range("mvc:user:animals:"+id, 0, -1);
        if(cacheAnimal.size() == 0){
            List<Animal> animals = animalRepository.getAnimalsByOwnerId(id);
            if(animals.isEmpty()){
                return null;
            }else{
                redisTemplate.opsForList().rightPushAll("mvc:user:animals:"+id, animals);
                redisTemplate.expire("mvc:user:animals:"+id, Duration.ofSeconds(60));
                return animals;
            }
        }
        return cacheAnimal;
    }

    @Override
    public void saveAnimal(Animal animal) {
        animalRepository.save(animal);
    }

    @Override
    public Animal getAnimalById(Long id) {
        Animal cacheAnimal = redisTemplate.opsForValue().get("mvc:animal");
        if(cacheAnimal == null){
            Optional<Animal> optional = animalRepository.findById(id);
            if(optional.isPresent()){
                Animal animal = optional.get();
                redisTemplate.opsForValue().set("mvc:animal",animal,Duration.ofSeconds(30));
                return animal;
            }
            else{
                throw new RuntimeException("Animal Not Found For Id::"+id);
            }
        }
       return cacheAnimal;
    }

    @Override
    public void deleteAnimalById(Long id) {
        animalRepository.deleteById(id);
    }

    @Override
    public List<Animal> findAllAnimals() {
         List<Animal> cahceAnimal =  redisTemplate.opsForList().range("mvc:animals",0,-1);
        if (cahceAnimal.size() == 0){
            List<Animal> animals = animalRepository.findAll();
            redisTemplate.opsForList().rightPushAll("mvc:animals", animals);
            redisTemplate.expire("mvc:animals", Duration.ofSeconds(60));
            return animals;
        }
        return cahceAnimal;
    }
}
