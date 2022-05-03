package ru.mirea.price.stealer.dto.api.cheapshark.deals.request;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListOfDealsRequestDto {
    private String storeID;
    private int pageNumber;
    private int pageSize;
    private String sortBy;
    private Boolean desc;
    private int lowerPrice;
    private int upperPrice;
    private int metacritic;
    private int steamRating;
    private String steamAppID;
    private String title;
    private Boolean exact;
    private Boolean AAA;
    private Boolean steamworks;
    private Boolean onSale;
    private String output;
}
