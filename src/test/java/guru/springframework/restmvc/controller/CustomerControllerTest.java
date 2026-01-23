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
import static org.mockito.Mockito.verify;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import org.mockito.ArgumentCaptor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Map;
import java.util.HashMap;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;


@WebMvcTest(CustomerController.class)
@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    CustomerService CustomerService;

    CustomerServiceImpl customerServiceImpl;

    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testPatchCustomer() throws Exception {
        Customer customer = customerServiceImpl.listCustomers().get(0);

        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("customerName", "New Customer Name");

        mockMvc.perform(patch("/api/v1/customer/" + customer.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(customerMap)))
            .andExpect(MockMvcResultMatchers.status().isNoContent());
            
        verify(CustomerService).patchCustomerById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());
        assertEquals(customer.getId(), uuidArgumentCaptor.getValue());
        assertEquals(customerMap.get("customerName"), customerArgumentCaptor.getValue().getCustomerName());
    }

    @Test
    void testDeleteCustomer() throws Exception {
        Customer customer = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(delete("/api/v1/customer/" + customer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isNoContent());

        
        ArgumentCaptor<UUID> argument = ArgumentCaptor.forClass(UUID.class);
        verify(CustomerService).deleteCustomerById(argument.capture());

        assertEquals(customer.getId(), argument.getValue());
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Customer customer = customerServiceImpl.listCustomers().get(0);

        mockMvc.perform(put("/api/v1/customer/" + customer.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(customer)))
            .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(CustomerService).updateCustomerById(any(UUID.class), any(Customer.class));
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
