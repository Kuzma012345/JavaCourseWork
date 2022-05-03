package ru.mirea.price.stealer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mirea.price.stealer.dto.api.cheapshark.deals.ListOfDealsDto;
import ru.mirea.price.stealer.dto.api.cheapshark.stores.StoreDto;
import ru.mirea.price.stealer.dto.domain.request.SearchInStoreRequestDto;
import ru.mirea.price.stealer.dto.domain.response.GameResponseDto;
import ru.mirea.price.stealer.service.PriceStealerService;
import ru.mirea.price.stealer.service.api.StoresApiService;
import ru.mirea.price.stealer.service.validation.StoreValidationService;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Controller
public class PriceStealerController {
    @Autowired
    private PriceStealerService priceStealerService;
    @Autowired
    private StoreValidationService storeValidationService;
    @Autowired
    private StoresApiService storesApiService;

    @GetMapping("/search/game")
    public String getCheapestPrice(Model model, @RequestParam(name = "title") String title) {
        model.addAttribute("gameResponses", priceStealerService.searchLowestGamePrice(title));
        return "cheapest-price";
    }

    @GetMapping("/search/game/info")
    public String searchCheapestPrice(Model model, @RequestParam(name = "title") String title) {
        model.addAttribute("gameResponses", priceStealerService.searchLowestGamePrice(title));
        return "cheapest-price-info";
    }

    @GetMapping("/search/store")
    public String showGameStores(Model model, @RequestParam(name = "title") String title) {
        List<GameResponseDto> gameResponseDtos = priceStealerService.searchLowestGamePrice(title);

//      show results without corrective choice if the game response size is 1
        if (Objects.nonNull(gameResponseDtos) && gameResponseDtos.size() == 1) {
            return showGameChoice(model, gameResponseDtos.iterator().next().getId());
        }

        model.addAttribute("gameResponses", gameResponseDtos);
        return "game-stores-correct-choice";
    }

    @GetMapping("/search/store/correct/game/choice")
    public String showGameChoice(Model model, @RequestParam(name = "id") String id) {
        model.addAttribute("gameLookupOptional", priceStealerService.showGameStores(id));
        return "game-stores-info";
    }

    @GetMapping("search/store/game")
    public String showGameStoresInfo(Model model, @RequestParam(name = "id") String id) {
        model.addAttribute("gameLookupOptional", priceStealerService.showGameStores(id));
        return "game-stores-info";
    }

    @GetMapping("search/store/game/params")
    public String searchInStoreByParams(SearchInStoreRequestDto searchInStoreRequestDto, Model model) {
        model.addAttribute("stores", storesApiService.storesInfo(Comparator.comparing(StoreDto::getStoreName)));

        List<String> errors = storeValidationService.validate(searchInStoreRequestDto);

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "store-search-info";
        }

        List<ListOfDealsDto> results = priceStealerService.searchInStore(searchInStoreRequestDto);

        if (results.isEmpty()) {
            errors.add("Unable to find anything on these parameters");
            model.addAttribute("errors", errors);
            return "store-search-info";
        }

        model.addAttribute("results", results);

        return "store-search-info";
    }
}
