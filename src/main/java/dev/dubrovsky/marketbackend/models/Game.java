package dev.dubrovsky.marketbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "games")
@Setter
@Getter
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "currency")
    private String currency;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    @JoinTable(
            name = "game_category",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnoreProperties("games")
    private Set<Category> categories = new HashSet<>();

    @Column(name = "rating")
    private Float rating;

    @Column(name = "date_added")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate dateAdded;

    @Column(name = "brand")
    private String brand;

    @OneToMany(mappedBy = "gameId", fetch = FetchType.LAZY)
    private Set<Image> images;

    @Column(name = "game_of_the_day")
    private boolean gameOfTheDay;

    @Column(name = "sale")
    private boolean sale;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "price_with_discount")
    private BigDecimal priceWithDiscount;
}
