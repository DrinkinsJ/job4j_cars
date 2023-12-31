package com.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "engines")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Builder
public class Engine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Column(name = "model", nullable = false)
    private String model;
}
