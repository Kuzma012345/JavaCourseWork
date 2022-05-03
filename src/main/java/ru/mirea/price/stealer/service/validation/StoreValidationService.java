package ru.mirea.price.stealer.service.validation;

import org.springframework.stereotype.Service;
import ru.mirea.price.stealer.dto.domain.request.SearchInStoreRequestDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StoreValidationService {

    public List<String> validate(SearchInStoreRequestDto dto) {
        List<String> errors = new ArrayList<>();

        String title = dto.getTitle();
        String lowerPrice = dto.getLowerPrice();
        String upperPrice = dto.getUpperPrice();

        if (title == null || title.isEmpty()) {
            errors.add("Store name cannot be empty");
        }

        Integer lowerPriceParsed = null;
        Integer upperPriceParsed = null;

        if (Objects.nonNull(lowerPrice)) {
            try {
                lowerPriceParsed = Integer.parseInt(lowerPrice);
            } catch (NumberFormatException | NullPointerException e) {
               errors.add("Incorrect lower price");
            }
        }

        if (Objects.nonNull(upperPrice)) {
            try {
                upperPriceParsed = Integer.parseInt(upperPrice);
            } catch (NumberFormatException | NullPointerException e) {
                errors.add("Incorrect upper price");
            }
        }

        if (lowerPriceParsed != null && upperPriceParsed != null && upperPriceParsed < lowerPriceParsed) {
            errors.add("Upper price cannot be more than lower price");
        }

        return errors;
    }
}
