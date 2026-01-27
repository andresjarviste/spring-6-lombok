package guru.springframework.restmvc.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeerDTO {
    private UUID id;
    private Integer version;

    @NotBlank
    @NotNull
    private String beerName;

    @NotBlank
    @NotNull
    private String upc;
    private Integer quantityOnHand;
    private BeerStyle beerStyle;
    
    @NotNull
    @PositiveOrZero
    @Digits(integer = 5, fraction = 2, message = "Invalid price")
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;

}
