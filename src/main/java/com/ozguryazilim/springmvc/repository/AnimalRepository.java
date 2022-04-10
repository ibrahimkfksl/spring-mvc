package com.ozguryazilim.springmvc.repository;

import com.ozguryazilim.springmvc.model.Animal;
import com.ozguryazilim.springmvc.model.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> getAnimalsByOwnerId(Long id);
}
