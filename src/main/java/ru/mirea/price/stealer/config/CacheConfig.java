package ru.mirea.price.stealer.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Ticker;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {
    public static final String ONE_HOUR_CACHE = "oneHourCache";
    public static final String ONE_MINUTE_CACHE = "oneMinuteCache";

    @Bean
    public CacheManager cacheManagerTicker(Ticker ticker) {

        List<Cache> caches = new ArrayList<>();

        caches.add(this.buildCache(ONE_HOUR_CACHE, ticker, 2000L, 1L, TimeUnit.HOURS));
        caches.add(this.buildCache(ONE_MINUTE_CACHE, ticker, 2000L, 1L, TimeUnit.MINUTES));

        SimpleCacheManager cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caches);
        return cacheManager;
    }

    private CaffeineCache buildCache(String cacheName, Ticker ticker, Long maxSize, Long ttl, TimeUnit ttlUnit){

        Caffeine<Object, Object> cacheBuilder = Caffeine.newBuilder();

        if (ttl != null && ttl > 0 && ttlUnit != null){
            cacheBuilder.expireAfterWrite(ttl, ttlUnit);
        }

        if (maxSize != null && maxSize > 0){
            cacheBuilder.maximumSize(maxSize);
        }

        cacheBuilder.ticker(ticker);

        return new CaffeineCache(cacheName, cacheBuilder.build());
    }

    @Bean
    public Ticker ticker() {
        return Ticker.systemTicker();
    }
}
