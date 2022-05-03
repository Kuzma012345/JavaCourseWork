package ru.mirea.price.stealer.dto.api.cheapshark.games;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.mirea.price.stealer.dto.AbstractGameLookupDto;

import java.util.List;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class GameLookupDto extends AbstractGameLookupDto {
    private List<GameLookupDealDto> deals;
}
