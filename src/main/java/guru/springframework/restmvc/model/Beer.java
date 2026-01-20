package guru.springframework.restmvc.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Beer {
    private UUID id;
    private Integer version;
    private String beerName;
    private String upc;
    private Integer quantityOnHand;
    private BeerStyle beerStyle;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

}
