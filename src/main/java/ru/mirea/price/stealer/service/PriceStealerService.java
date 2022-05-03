package ru.mirea.price.stealer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.mirea.price.stealer.config.CacheConfig;
import ru.mirea.price.stealer.dto.api.cheapshark.deals.ListOfDealsDto;
import ru.mirea.price.stealer.dto.api.cheapshark.deals.request.ListOfDealsRequestDto;
import ru.mirea.price.stealer.dto.api.cheapshark.games.GameDto;
import ru.mirea.price.stealer.dto.api.cheapshark.games.GameLookupDealDto;
import ru.mirea.price.stealer.dto.api.cheapshark.games.GameLookupDto;
import ru.mirea.price.stealer.dto.api.cheapshark.games.request.ListOfGamesRequestDto;
import ru.mirea.price.stealer.dto.api.cheapshark.stores.StoreDto;
import ru.mirea.price.stealer.dto.domain.request.SearchInStoreRequestDto;
import ru.mirea.price.stealer.dto.domain.response.GameLookupDealResponseDto;
import ru.mirea.price.stealer.dto.domain.response.GameLookupResponseDto;
import ru.mirea.price.stealer.dto.domain.response.GameResponseDto;
import ru.mirea.price.stealer.service.api.DealsApiService;
import ru.mirea.price.stealer.service.api.GamesApiService;
import ru.mirea.price.stealer.service.api.StoresApiService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PriceStealerService {
    @Autowired
    private GamesApiService gamesApiService;
    @Autowired
    private StoresApiService storesApiService;
    @Autowired
    private DealsApiService dealsApiService;

    @Cacheable(cacheNames = {CacheConfig.ONE_MINUTE_CACHE}, cacheManager = "cacheManagerTicker")
    public List<GameResponseDto> searchLowestGamePrice(String title) {
        ListOfGamesRequestDto requestDto = ListOfGamesRequestDto.builder()
                .title(title)
                .build()
        ;

        List<GameDto> gameDtos = gamesApiService.listOfGames(requestDto);

//      found games by not exact title
        List<String> gamesIds = getGameIds(gameDtos);

        return enrichAndCreateGameResponses(gamesApiService.multipleGameLookup(gamesIds));
    }

    public Optional<GameLookupResponseDto> showGameStores(String gameId) {
        Optional<GameLookupDto> gameLookupDtoOptional = gamesApiService.gameLookup(gameId);

        if (gameLookupDtoOptional.isEmpty()) {
            log.error("Unable to get game lookup by game id: {}, creating skipped", gameId);
            return Optional.empty();
        }

        GameLookupDto gameLookupDto = gameLookupDtoOptional.get();
        GameLookupResponseDto gameLookupResponseDto = buildGameLookupResponseDto(gameLookupDto);

        return Optional.of(gameLookupResponseDto);
    }

    public List<ListOfDealsDto> searchInStore(SearchInStoreRequestDto searchDto) {
        Optional<ListOfDealsRequestDto> listOfDealsRequestOptional = buildListOfDealsRequestDto(searchDto);

        if (listOfDealsRequestOptional.isEmpty()) {
            return Collections.emptyList();
        }

        ListOfDealsRequestDto requestDto = listOfDealsRequestOptional.get();
        List<ListOfDealsDto> listOfDealsDtos = dealsApiService.listOfDeals(requestDto);

        if (listOfDealsDtos.isEmpty()) {
            log.info("Nothing was found in list of deals by request:{}", requestDto);
            return Collections.emptyList();
        }


        return listOfDealsDtos;
    }

    private Optional<ListOfDealsRequestDto> buildListOfDealsRequestDto(SearchInStoreRequestDto searchDto) {
        Optional<StoreDto> storeInfoOptional = storesApiService.getStoreInfoByName(searchDto.getTitle());
        Optional<Integer> lowerPrice = getLowerPrice(searchDto.getLowerPrice());
        Optional<Integer> upperPrice = getUpperPrice(searchDto.getUpperPrice());

        if (storeInfoOptional.isEmpty()) {
            return Optional.empty();
        }

        if (lowerPrice.isEmpty()) {
            return Optional.empty();
        }

        if (upperPrice.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(ListOfDealsRequestDto.builder()
                .lowerPrice(lowerPrice.get())
                .upperPrice(upperPrice.get())
                .pageSize(60)
                .build()
        );
    }

    private Optional<Integer> getLowerPrice(String lowerPrice) {
        try {
            return Optional.of(Integer.parseInt(lowerPrice));
        } catch (NumberFormatException | NullPointerException e) {
            log.warn("Unable to get lower price from value:{}", lowerPrice);
        }

        return Optional.empty();
    }

    private Optional<Integer> getUpperPrice(String upperPrice) {
        try {
            return Optional.of(Integer.parseInt(upperPrice));
        } catch (NumberFormatException | NullPointerException e) {
            log.warn("Unable to get upper price from value:{}", upperPrice);
        }

        return Optional.empty();
    }

    private List<GameResponseDto> enrichAndCreateGameResponses(Map<String, GameLookupDto> multipleGameLookup) {
        List<GameResponseDto> gameResponseDtos = new ArrayList<>();

        for (Map.Entry<String, GameLookupDto> entry : multipleGameLookup.entrySet()) {
            Optional<GameLookupDealDto> gameLookupDealDtoOptional = getCheapestDeal(entry.getValue());

            if (gameLookupDealDtoOptional.isEmpty()) {
                log.warn("Not found game lookup for game with id:{}, creating game response skipped", entry.getKey());
                continue;
            }

            GameLookupDealDto gameLookupDealDto = gameLookupDealDtoOptional.get();
            String storeId = gameLookupDealDto.getStoreID();
            Optional<StoreDto> storeInfoOptional = storesApiService.getStoreInfoById(storeId);

            if (storeInfoOptional.isEmpty()) {
                log.warn("Not found store name by id:{}, creating game response skipped", storeId);
                continue;
            }

            String storeName = storeInfoOptional.get().getStoreName();

            gameResponseDtos.add(buildGameResponse(entry, gameLookupDealDto, storeName));
        }

        return gameResponseDtos;
    }

    private Optional<GameLookupDealDto> getCheapestDeal(GameLookupDto gameLookupDto) {
        return gameLookupDto
                .getDeals()
                .stream()
                .min((o1, o2) -> Float.compare(Float.parseFloat(o1.getPrice()), Float.parseFloat(o2.getPrice())))
        ;
    }

    private List<String> getGameIds(List<GameDto> gameDtos) {
        return gameDtos.stream()
                .map(GameDto::getGameID)
                .collect(Collectors.toList())
        ;
    }

    private GameResponseDto buildGameResponse(Map.Entry<String, GameLookupDto> entry, GameLookupDealDto gameLookupDealDto, String storeName) {
        return GameResponseDto.builder()
                .id(entry.getKey())
                .name(entry.getValue().getInfo().getTitle())
                .storeName(storeName)
                .salePrice(gameLookupDealDto.getPrice())
                .retailPrice(gameLookupDealDto.getRetailPrice())
                .cheapestDealID(gameLookupDealDto.getDealID())
                .build()
        ;
    }

    private GameLookupResponseDto buildGameLookupResponseDto(GameLookupDto gameLookupDto) {
        return GameLookupResponseDto.builder()
                .info(gameLookupDto.getInfo())
                .cheapestPriceEver(gameLookupDto.getCheapestPriceEver())
                .deals(mapGameLookupDeals(gameLookupDto))
                .build()
        ;
    }

    private List<GameLookupDealResponseDto> mapGameLookupDeals(GameLookupDto gameLookupDto) {
        return gameLookupDto.getDeals().stream()
                .filter(gl -> Objects.nonNull(gl.getStoreID()))
                .map(this::buildGameLookupDealResponseDto)
                .collect(Collectors.toList())
        ;
    }

    private GameLookupDealResponseDto buildGameLookupDealResponseDto(GameLookupDealDto gl) {
        GameLookupDealResponseDto dealResponse = GameLookupDealResponseDto.builder()
                .storeID(gl.getStoreID())
                .price(gl.getPrice())
                .retailPrice(gl.getRetailPrice())
                .savings(gl.getSavings())
                .build()
        ;

        storesApiService
                .getStoreInfoById(gl.getStoreID())
                .ifPresent(dto -> dealResponse.setStoreName(dto.getStoreName()))
        ;

        return dealResponse;
    }
}
