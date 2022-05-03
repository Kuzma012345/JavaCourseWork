package ru.mirea.price.stealer.dto.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameResponseDto {
    private String id;
    private String name;
    private String storeName;
    private String salePrice;
    private String retailPrice;
    private String cheapestDealID;
}
