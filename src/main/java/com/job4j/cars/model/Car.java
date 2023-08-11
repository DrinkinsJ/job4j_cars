package com.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "car")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
@Setter
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "history_owners",
            joinColumns = {@JoinColumn(name = "car_id")},
            inverseJoinColumns = {@JoinColumn(name = "owner_id")}
    )
    private Set<Owner> owners = new HashSet<>();

}
