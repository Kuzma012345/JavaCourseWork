package ru.mirea.price.stealer.dto.api.cheapshark.deals;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListOfDealsDto {
    private String internalName;
    private String title;
    private String metacriticLink;
    private String dealID;
    private String storeID;
    private String gameID;
    private String salePrice;
    private String normalPrice;
    private String isOnSale;
    private String savings;
    private String metacriticScore;
    private String steamRatingText;
    private String steamRatingPercent;
    private String steamRatingCount;
    private String steamAppID;
    private int releaseDate;
    private int lastChange;
    private String dealRating;
    private String thumb;
}
