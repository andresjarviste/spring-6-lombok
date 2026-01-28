package guru.springframework.restmvc.repositories;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import guru.springframework.restmvc.entities.Beer;
import guru.springframework.restmvc.repositories.BeerRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;

@Testcontainers
@ActiveProfiles("localmysql")
@SpringBootTest
public class MySqlTestIT {
    
    @Container
    @ServiceConnection
    static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.0.32");

    // You need this when  you do not use ServiceConnection annotation
    // @DynamicPropertySource
    // static void mysqlProperties(DynamicPropertyRegistry registry) {
    //     registry.add("spring.datasource.url", mysql::getJdbcUrl);
    //     registry.add("spring.datasource.username", mysql::getUsername);
    //     registry.add("spring.datasource.password", mysql::getPassword);
    // }

    // use this to test dataSource parameters 
    //@Autowired
    // private DataSource dataSource;

    @Autowired
    private BeerRepository beerRepository;

    @Test
    void testListBeers() {
        List<Beer> beers = beerRepository.findAll();
        assertThat(beers.size()).isGreaterThan(0);
        assertThat(beers.size()).isEqualTo(3);
    }
}
