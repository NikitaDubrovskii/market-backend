package dev.dubrovsky.marketbackend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class ShopGameKey implements Serializable {

    @Column(name = "shop_id")
    private Long shopId;

    @Column(name = "game_id")
    private Long gameId;

}
