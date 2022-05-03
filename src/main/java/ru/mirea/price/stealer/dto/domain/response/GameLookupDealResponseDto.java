package ru.mirea.price.stealer.dto.domain.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import ru.mirea.price.stealer.dto.AbstractGameLookupDealDto;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class GameLookupDealResponseDto extends AbstractGameLookupDealDto {
    private String storeName;
}
