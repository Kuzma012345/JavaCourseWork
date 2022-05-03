package ru.mirea.price.stealer.dto.domain.request;

import lombok.Data;

@Data
public class SearchInStoreRequestDto {
    private String title;
    private String lowerPrice;
    private String upperPrice;
}
