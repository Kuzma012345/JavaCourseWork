package ru.mirea.price.stealer.dto.api.cheapshark.games;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.mirea.price.stealer.dto.AbstractGameLookupDealDto;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class GameLookupDealDto extends AbstractGameLookupDealDto {
}
