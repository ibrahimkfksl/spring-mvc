package com.ozguryazilim.springmvc.service.impl;


import com.ozguryazilim.springmvc.model.Animal;
import com.ozguryazilim.springmvc.model.Role;
import com.ozguryazilim.springmvc.model.User;
import com.ozguryazilim.springmvc.model.enums.AnimalType;
import com.ozguryazilim.springmvc.repository.AnimalRepository;
import com.ozguryazilim.springmvc.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class AnimalServiceImplTest {

    @InjectMocks
    private AnimalServiceImpl animalService;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private UserService userService;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    private User testUser;

    private Animal firstTestAnimal;

    private Animal secondTestAnimal;

    @BeforeEach
    public void setup(){
        Role role = new Role();
        role.setId(1L);
        role.setName("TEST_USER");
        given(passwordEncoder.encode(ArgumentMatchers.any(String.class))).willReturn("12345678");
        testUser = new User();
        testUser.setId(1L);
        testUser.setFirstName("test-firstname");
        testUser.setLastName("test-lastname");
        testUser.setEmail("test-email@hotmail.com");
        testUser.setPassword(passwordEncoder.encode("12345678".toString()));
        testUser.setRoles(Arrays.asList(role));


        firstTestAnimal = new Animal();
        firstTestAnimal.setName("first-test-animal-name");
        firstTestAnimal.setId(2L);
        firstTestAnimal.setType(AnimalType.BIRD);
        firstTestAnimal.setAge(2);
        firstTestAnimal.setOwner(testUser);
        firstTestAnimal.setDescription("first-test-animal-description");

        secondTestAnimal = new Animal();
        secondTestAnimal.setName("second-test-animal-name");
        secondTestAnimal.setId(3L);
        secondTestAnimal.setType(AnimalType.BIRD);
        secondTestAnimal.setAge(3);
        secondTestAnimal.setOwner(testUser);
        secondTestAnimal.setDescription("second-test-animal-description");

    }

    @Test
    void givenUsername_whenGetAnimalsByUserPrincipal_thenReturnAnimals(){
        //given
        List<Animal> expectedAnimal = Arrays.asList(firstTestAnimal, secondTestAnimal);
        given(userService.getUserIdByUsername(testUser.getEmail())).willReturn(1L);
        given(animalRepository.getAnimalsByOwnerId(1L)).willReturn(expectedAnimal);

        //when
        List<Animal> resultAnimal = animalService.getAnimalsByUserPrincipal(testUser.getEmail());

        //then
        assertThat(expectedAnimal).isEqualTo(resultAnimal);

    }

    @Test
    void givenAnimal_whenSaveAnimal_thenReturnNothing(){

        //given
        given(animalRepository.save(firstTestAnimal)).willReturn(firstTestAnimal);

        //when
        animalService.saveAnimal(firstTestAnimal);

        //then
        verify(animalRepository, times(1)).save(firstTestAnimal);

    }

    @Test
    void givenAnimalId_whenGetAnimalById_thenReturnAnimal(){

        //given
        given(animalRepository.findById(1L)).willReturn(Optional.of(firstTestAnimal));

        //when
        Animal animal = animalService.getAnimalById(1L);

        //then
        assertThat(firstTestAnimal).isEqualTo(animal);
    }

    @Test
    void givenAnimalId_whenDeleteAnimalById_thenReturnNothing(){

        //when
        animalService.deleteAnimalById(1L);

        //then
        verify(animalRepository).deleteById(1L);

    }

    @Test
    void should_return_all_animals(){

        //given
        List<Animal> animals = Arrays.asList(firstTestAnimal, secondTestAnimal);
        given(animalRepository.findAll()).willReturn(animals);

        //when
        List<Animal> resultAnimal = animalService.findAllAnimals();

        //then
        assertThat(animals).isEqualTo(resultAnimal);

    }


    @Test
    void givenAnimalName_whenSearchByAnimalName_thenReturnAnimalSet(){
        //given
        Set<Animal> animalSet = new HashSet<>();
        animalSet.add(firstTestAnimal);
        animalSet.add(secondTestAnimal);
        given(userService.getUserIdByUsername(testUser.getEmail())).willReturn(1L);
        given(animalRepository.findAnimalByName("test",1L)).willReturn(animalSet);

        //when
        Set<Animal> resultAnimalSet = animalService.searchByAnimalName("test",testUser.getEmail());

        //then
        assertThat(animalSet).isEqualTo(resultAnimalSet);

    }

    @Test
    void givenAnimalName_whenSearchByAnimalNameOrOwnerName_thenReturnAnimalSet(){

        //given
        Set<Animal> animalSet = new HashSet<>();
        animalSet.add(firstTestAnimal);
        animalSet.add(secondTestAnimal);
        given(animalRepository.findAnimalByOwnerFirstNameOrName("first")).willReturn(animalSet);

        //when
        Set<Animal> resultAnimalSet = animalService.searchByAnimalNameOrOwnerName("first");

        //then
        assertThat(animalSet).isEqualTo(resultAnimalSet);
    }

}