package dev.dubrovsky.marketbackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Shop_Game")
@Getter
@Setter
public class ShopGame {

    @EmbeddedId
    private ShopGameKey shopGameKeyId;

    @ManyToOne
    @MapsId("shopId")
    @JoinColumn(name = "shop_id")
    private Shop shopId;

    @ManyToOne
    @MapsId("gameId")
    @JoinColumn(name = "game_Id")
    private Game gameId;

    private Integer quantity;

}
