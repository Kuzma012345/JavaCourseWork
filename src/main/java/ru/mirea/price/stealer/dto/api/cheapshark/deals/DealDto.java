package ru.mirea.price.stealer.dto.api.cheapshark.deals;

import lombok.Data;

import java.util.List;

@Data
public class DealDto {
    public GameInfoDto gameInfo;
    public List<Object> cheaperStores;
    public CheapestPriceDto cheapestPrice;
}
