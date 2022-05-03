package ru.mirea.price.stealer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class AbstractGameLookupDealDto {
    private String storeID;
    private String dealID;
    private String price;
    private String retailPrice;
    private String savings;
}
