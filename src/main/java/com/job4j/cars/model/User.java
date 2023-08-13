package com.job4j.cars.model;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "auto_user")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String login;
    private String password;
}