package ru.mirea.price.stealer.dto.domain.response;

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
public class GameLookupResponseDto extends AbstractGameLookupDto {
    private List<GameLookupDealResponseDto> deals;
}
