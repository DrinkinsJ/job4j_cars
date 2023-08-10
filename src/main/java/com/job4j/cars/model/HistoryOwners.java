package com.job4j.cars.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "history_owners")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HistoryOwners {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private int carId;
    private int ownerId;
}
