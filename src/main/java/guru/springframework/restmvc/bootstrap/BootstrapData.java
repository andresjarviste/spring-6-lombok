package guru.springframework.restmvc.bootstrap;

import guru.springframework.restmvc.entities.Beer;
import guru.springframework.restmvc.entities.Customer;

import guru.springframework.restmvc.model.BeerStyle;
import java.math.BigDecimal;

import guru.springframework.restmvc.repositories.BeerRepository;
import guru.springframework.restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    
    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
        //System.out.println("Bootstrap Data NOT loaded");
    }

    private void loadBeerData() {
        Beer beer1 = Beer.builder()
            .beerName("Galaxy Cat")
            .beerStyle(BeerStyle.PALE_ALE)
            .upc("12356")
            .price(new BigDecimal("12.99"))
            .quantityOnHand(122)
            .build();

        beerRepository.save(beer1);

        Beer beer2 = Beer.builder()
            .beerName("Crank")
            .beerStyle(BeerStyle.PALE_ALE)
            .upc("12356222")
            .price(new BigDecimal("11.99"))
            .quantityOnHand(392)
            .build();
        beerRepository.save(beer2);

        Beer beer3 = Beer.builder()
            .beerName("Sunshine City")
            .beerStyle(BeerStyle.IPA)
            .upc("12356")
            .price(new BigDecimal("13.99"))
            .quantityOnHand(144)
            .build();
        beerRepository.save(beer3);
    }

    private void loadCustomerData() {
        Customer customer1 = Customer.builder()
            .customerName("John Doe")
            .build();
        customerRepository.save(customer1);
        Customer customer2 = Customer.builder()
            .customerName("Jane Smith")
            .build();
        customerRepository.save(customer2);
    }

}
