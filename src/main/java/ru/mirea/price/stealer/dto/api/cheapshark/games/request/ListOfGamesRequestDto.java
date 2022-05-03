package ru.mirea.price.stealer.dto.api.cheapshark.games.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ListOfGamesRequestDto {
    String title;
    String steamAppID;
    String limit;
    String exact;
}
