package ru.mirea.price.stealer.dto.api.cheapshark.deals;

import lombok.Data;

@Data
public class GameInfoDto {
    private String storeID;
    private String gameID;
    private String name;
    private String steamAppID;
    private String salePrice;
    private String retailPrice;
    private String steamRatingText;
    private String steamRatingPercent;
    private String steamRatingCount;
    private String metacriticScore;
    private String metacriticLink;
    private int releaseDate;
    private String publisher;
    private String steamworks;
    private String thumb;
}
