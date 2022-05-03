package ru.mirea.price.stealer.service.api;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.mirea.price.stealer.api.cheapshark.feign.StoresApi;
import ru.mirea.price.stealer.config.CacheConfig;
import ru.mirea.price.stealer.dto.api.cheapshark.stores.StoreDto;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StoresApiService {
    @Autowired
    private StoresApi storesApi;

    public List<StoreDto> storesInfo() {
        try {
            return storesApi.storesInfo();
        } catch (FeignException.NotFound e) {
            log.info("Stores info not found. {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unable to get stores info", e);
        }

        return Collections.emptyList();
    }

    public List<StoreDto> storesInfo(Comparator<StoreDto> c) {
        List<StoreDto> storeDtos = storesInfo();
        storeDtos.sort(c);
        return storeDtos;
    }

    @Cacheable(cacheNames = {CacheConfig.ONE_HOUR_CACHE}, cacheManager = "cacheManagerTicker")
    public Optional<StoreDto> getStoreInfoById(String id) {
        Map<String, StoreDto> storeDtoMap = matchStoreInfoById();
        Optional<StoreDto> storeDtoOptional = Optional.ofNullable(storeDtoMap.get(id));

        if (storeDtoOptional.isEmpty()) {
            log.error("Unable to get store info with id:{} from storeInfo map:{}", id, storeDtoMap);
        }

        return storeDtoOptional;
    }

    public Optional<StoreDto> getStoreInfoByName(String name) {
        Map<String, StoreDto> storeDtoMap = matchStoreInfoByName();
        Optional<StoreDto> storeDtoOptional = Optional.ofNullable(storeDtoMap.get(name));

        if (storeDtoOptional.isEmpty()) {
            log.error("Unable to get store info with name:{} from storeInfo map:{}", name, storeDtoMap);
        }

        return storeDtoOptional;
    }

    private Map<String, StoreDto> matchStoreInfoById() {
        return storesInfo().stream().collect(Collectors.toMap(StoreDto::getStoreID, e -> e));
    }

    private Map<String, StoreDto> matchStoreInfoByName() {
        return storesInfo().stream().collect(Collectors.toMap(StoreDto::getStoreName, e -> e));
    }
}
