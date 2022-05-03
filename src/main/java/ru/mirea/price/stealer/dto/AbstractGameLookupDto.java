package ru.mirea.price.stealer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.mirea.price.stealer.dto.api.cheapshark.games.CheapestPriceEverDto;
import ru.mirea.price.stealer.dto.api.cheapshark.games.InfoDto;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AbstractGameLookupDto {
    private InfoDto info;
    private CheapestPriceEverDto cheapestPriceEver;
}
