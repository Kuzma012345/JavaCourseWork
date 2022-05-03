package ru.mirea.price.stealer.api.cheapshark.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import ru.mirea.price.stealer.dto.api.cheapshark.stores.StoreDto;

import java.util.List;

@FeignClient(
        name = "cheapshark-stores-api-client",
        url = "${feign.cheapshark-api.url:}",
        path = "${feign.cheapshark-api.path:}"
)
public interface StoresApi {

    @GetMapping("/stores")
    List<StoreDto> storesInfo();
}
