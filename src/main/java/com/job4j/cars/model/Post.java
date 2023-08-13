package com.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "auto_post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String description;
    private LocalDateTime created = LocalDateTime.now();

    @ToString.Include
    @ManyToOne
    @JoinColumn(name = "auto_user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "auto_post_id")
    private List<PriceHistory> priceHistory = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "participates",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> participates = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "auto_post_id", foreignKey = @ForeignKey(name = "car_photo_auto_post_id_fkey"))
    private List<CarPhoto> photos = new ArrayList<>();
}
