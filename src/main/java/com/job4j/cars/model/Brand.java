package com.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "car_brands")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @Column(name = "brand", nullable = false)
    private String brand;
}
