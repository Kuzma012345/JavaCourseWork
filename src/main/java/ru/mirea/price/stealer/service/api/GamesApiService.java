package ru.mirea.price.stealer.service.api;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.price.stealer.api.cheapshark.feign.GamesApi;
import ru.mirea.price.stealer.dto.api.cheapshark.games.GameDto;
import ru.mirea.price.stealer.dto.api.cheapshark.games.GameLookupDto;
import ru.mirea.price.stealer.dto.api.cheapshark.games.request.ListOfGamesRequestDto;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class GamesApiService {
    @Autowired
    private GamesApi gamesApi;

    public List<GameDto> listOfGames(ListOfGamesRequestDto dto) {
        try {
            return gamesApi.listOfGames(dto);
        } catch (FeignException.NotFound e) {
            log.info("List of games not found by params:{}. {}", dto, e.getMessage());
        } catch (Exception e) {
            log.error("Unable to get list of games by params:" + dto, e);
        }

        return Collections.emptyList();
    }

    public Optional<GameLookupDto> gameLookup(String id) {
        try {
            return Optional.ofNullable(gamesApi.gameLookup(id));
        } catch (FeignException.NotFound e) {
            log.info("Game lookup not found by id:{}. {}", id, e.getMessage());
        } catch (Exception e) {
            log.error("Unable to get game lookup by id:" + id, e);
        }
        return Optional.empty();
    }

    public Map<String, GameLookupDto> multipleGameLookup(List<String> ids) {
        try {
            return gamesApi.multipleGameLookup(String.join(",", ids));
        } catch (FeignException.NotFound e) {
            log.info("Multiple game lookup not found by ids:{}. {}", ids, e.getMessage());
        } catch (Exception e) {
            log.error("Unable to get multiple game lookup by ids:" + ids, e);
        }

        return Collections.emptyMap();
    }
}
