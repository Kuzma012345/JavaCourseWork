package ru.mirea.price.stealer.api.cheapshark.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mirea.price.stealer.dto.api.cheapshark.games.GameDto;
import ru.mirea.price.stealer.dto.api.cheapshark.games.GameLookupDto;
import ru.mirea.price.stealer.dto.api.cheapshark.games.request.ListOfGamesRequestDto;

import java.util.List;
import java.util.Map;

@FeignClient(
        name = "cheapshark-games-api-client",
        url = "${feign.cheapshark-api.url:}",
        path = "${feign.cheapshark-api.path:}"
)
public interface GamesApi {

    @GetMapping("/games")
    List<GameDto> listOfGames(@SpringQueryMap ListOfGamesRequestDto dto);

    @GetMapping("/games")
    GameLookupDto gameLookup(@RequestParam(name = "id") String id);

    /**
     * Allows lookup of a list of games. Includes list of all deals for each game. Maximum of 25 games.
     * @param ids A comma seperated list of GameID's
     */
    @GetMapping("/games")
    Map<String, GameLookupDto> multipleGameLookup(@RequestParam(name = "ids") String ids);
}
