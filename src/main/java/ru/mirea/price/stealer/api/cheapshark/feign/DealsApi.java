package ru.mirea.price.stealer.api.cheapshark.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mirea.price.stealer.dto.api.cheapshark.deals.DealDto;
import ru.mirea.price.stealer.dto.api.cheapshark.deals.ListOfDealsDto;
import ru.mirea.price.stealer.dto.api.cheapshark.deals.request.ListOfDealsRequestDto;

import java.util.List;

@FeignClient(
        name = "cheapshark-deals-api-client",
        url = "${feign.cheapshark-api.url:}",
        path = "${feign.cheapshark-api.path:}"
)
public interface DealsApi {

    @GetMapping("/deals")
    DealDto dealLookup(@RequestParam(name = "id") String id);

    @GetMapping("/deals")
    List<ListOfDealsDto> listOfDeals(@SpringQueryMap ListOfDealsRequestDto requestDto);
}
