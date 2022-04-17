package com.ozguryazilim.springmvc.service.impl;

import com.ozguryazilim.springmvc.model.Animal;
import com.ozguryazilim.springmvc.model.enums.AnimalType;
import com.ozguryazilim.springmvc.repository.AnimalRepository;
import com.ozguryazilim.springmvc.service.AnimalService;
import com.ozguryazilim.springmvc.service.UserService;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class AnimalServiceImplTest {

    @Autowired
    private AnimalService animalService;

    @Test
    void give_username_then_return_animals() {
        String username="test-email@hotmail.com";
        List<Animal> animalList = animalService.getAnimalsByUserPrincipal(username);
        assertThat(animalList)
                .hasSize(2)
                .extracting("name","description")
                .containsExactlyInAnyOrder(
                        tuple("test-name1","test-description1"),
                        tuple("test-name2","test-description2")
                );
    }

    @Test
    void give_animalId_then_return_animal_entity() {
        Long animalId = 1L;
        Animal animal = animalService.getAnimalById(animalId);
        assertThat(animal.getId()).isEqualTo(1L);
        assertThat(animal.getType()).isEqualTo(AnimalType.DOG);
        assertThat(animal.getAge()).isEqualTo(2);
        assertThat(animal.getDescription()).isEqualTo("test-description1");
        assertThat(animal.getName()).isEqualTo("test-name1");
        assertThat(animal.getOwner().getId()).isEqualTo(1L);
    }


    @Test
    void find_all_animals() {
        List<Animal> animals = animalService.findAllAnimals();
        assertThat(animals).isNotNull().extracting("name").contains("test-name1");
        assertThat(animals).isNotNull().extracting("name").contains("test-name2");

    }
}