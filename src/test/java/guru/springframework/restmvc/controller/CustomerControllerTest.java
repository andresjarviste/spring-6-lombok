package guru.springframework.restmvc.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;
import guru.springframework.restmvc.model.Customer;
import static org.mockito.BDDMockito.given;
import org.springframework.http.HttpHeaders;

import guru.springframework.restmvc.services.CustomerService;
import guru.springframework.restmvc.services.CustomerServiceImpl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import tools.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CustomerService CustomerService;

    CustomerServiceImpl customerServiceImpl;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testListCustomers() throws Exception {
        given(CustomerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());

        mockMvc.perform(MockMvcRequestBuilders
            .get("/api/v1/customer")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    void testCreateBeer() throws Exception {
        Customer customer = customerServiceImpl.listCustomers().get(0);

        customer.setVersion(null);
        customer.setId(null);
        customer.setCustomerName("Test Customer Name");
        
        given(CustomerService.saveNewCustomer(any(Customer.class))).willReturn(customerServiceImpl.listCustomers().get(1));

        mockMvc.perform(post("/api/v1/customer")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(customer)))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.header().exists(HttpHeaders.LOCATION));
    }
        
}
