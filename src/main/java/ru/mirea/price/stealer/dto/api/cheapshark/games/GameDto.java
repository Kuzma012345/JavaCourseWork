package ru.mirea.price.stealer.dto.api.cheapshark.games;

import lombok.Data;

@Data
public class GameDto {
    private String gameID;
    private String steamAppID;
    private String cheapest;
    private String cheapestDealID;
    private String external;
    private String thumb;
}
