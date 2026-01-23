package guru.springframework.restmvc.mappers;

import guru.springframework.restmvc.entities.Beer;
import guru.springframework.restmvc.model.BeerDTO;

import org.mapstruct.Mapper;
@Mapper
public interface BeerMapper {
    BeerDTO beerToBeerDTO(Beer beer);
    Beer beerDTOToBeer(BeerDTO beerDTO);
}
