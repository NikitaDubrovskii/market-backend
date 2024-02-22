package dev.dubrovsky.marketbackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "games")
@Setter
@Getter
/*@ToString(
        of = {"gameId", "name", "description", "price",
        "currency", "quantity", "categories", "image",
        "rating", "dateAdded", "brand"}
)*/
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

    @Column(name = "image")         //сделать как то фотографии
    private String image;

    @Column(name = "rating")
    private Float rating;

    @Column(name = "date_added")
    private Date dateAdded;

    @Column(name = "brand")
    private String brand;
}
