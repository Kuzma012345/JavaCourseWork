package ru.mirea.price.stealer.dto.api.cheapshark.stores;

import lombok.Data;

@Data
public class StoreDto {
    private String storeID;
    private String storeName;
    private int isActive;
    private ImagesDto images;
}
