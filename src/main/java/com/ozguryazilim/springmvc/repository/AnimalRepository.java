package com.ozguryazilim.springmvc.repository;

import com.ozguryazilim.springmvc.model.Animal;
import com.ozguryazilim.springmvc.model.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> getAnimalsByOwnerId(Long id);

    @Query(value = "select * from animal a, users u where a.owner_id=u.id and a.name like %:keyword% and u.id=:id ", nativeQuery = true)
    Set<Animal> findAnimalByName(@Param("keyword") String keyword, @Param("id") Long id);

    @Query(value = "select * from animal a, users u where a.owner_id = u.id and (a.name like %:keyword% or u.first_name like %:keyword%)", nativeQuery = true)
    Set<Animal> findAnimalByOwnerFirstNameOrName(@Param("keyword") String keyword);
}
