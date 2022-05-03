package ru.mirea.price.stealer.service.api;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.price.stealer.api.cheapshark.feign.DealsApi;
import ru.mirea.price.stealer.dto.api.cheapshark.deals.DealDto;
import ru.mirea.price.stealer.dto.api.cheapshark.deals.ListOfDealsDto;
import ru.mirea.price.stealer.dto.api.cheapshark.deals.request.ListOfDealsRequestDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DealsApiService {
    @Autowired
    private DealsApi dealsApi;

    public Optional<DealDto> dealLookup(String id) {
        try {
            return Optional.ofNullable(dealsApi.dealLookup(id));
        } catch (FeignException.NotFound e) {
            log.info("Deal with id:{} not found. {}", id, e.getMessage());
        } catch (Exception e) {
            log.error("Unable to get deal with id:" + id, e);
        }

        return Optional.empty();
    }

    public List<ListOfDealsDto> listOfDeals(ListOfDealsRequestDto requestDto) {
        try {
            return dealsApi.listOfDeals(requestDto);
        } catch (FeignException.NotFound e) {
            log.info("List of deals not found. Parameters: {}. {}", requestDto, e.getMessage());
        } catch (Exception e) {
            log.error("Unable to get list of deals with parameters: " + requestDto, e);
        }

        return Collections.emptyList();
    }
}
