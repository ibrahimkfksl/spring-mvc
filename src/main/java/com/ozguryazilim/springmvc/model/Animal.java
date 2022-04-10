package com.ozguryazilim.springmvc.model;

import com.ozguryazilim.springmvc.model.enums.AnimalType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private String name;

    @Enumerated(EnumType.STRING)
    private AnimalType type;

    private int age;

    private String description;

    @ManyToOne
    private User owner;
}
