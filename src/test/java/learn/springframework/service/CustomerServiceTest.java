package learn.springframework.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import learn.springframework.api.v1.mapper.CustomerMapper;
import learn.springframework.api.v1.model.CustomerDTO;
import learn.springframework.domain.Customer;
import learn.springframework.repositories.CustomerRepository;

public class CustomerServiceTest {

	CustomerService customerService;

	@Mock
	CustomerRepository customerRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
	}

	@Test
	public void getAllCustomers() throws Exception {
		// given
		Customer customer1 = new Customer();
		customer1.setId(1l);
		customer1.setFirstName("Michale");
		customer1.setLastName("Weston");

		Customer customer2 = new Customer();
		customer2.setId(2l);
		customer2.setFirstName("Sam");
		customer2.setLastName("Axe");

		when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));

		// when
		List<CustomerDTO> customerDTOS = customerService.getAllCustomers();

		// then
		assertEquals(2, customerDTOS.size());

	}

	@Test
	public void getCustomerById() throws Exception {
		// given
		Customer customer1 = new Customer();
		customer1.setId(1l);
		customer1.setFirstName("Michale");
		customer1.setLastName("Weston");

		when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(customer1));

		// when
		CustomerDTO customerDTO = customerService.getCustomerById(1L);

		// then
		assertEquals("Michale", customerDTO.getFirstName());
	}
	
	@Test
	public void createNewCustomer() throws Exception {
		
		//given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Jim");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setId(1l);
		
		// when
		when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
		
		CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);
		
		//then
        assertEquals(customerDTO.getFirstName(), savedDto.getFirstName());
        assertEquals("/api/v1/customers/1", savedDto.getCustomerUrl());
	}
	
	@Test
    public void saveCustomerByDTO() throws Exception {

        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Jim");
        customerDTO.setLastName("Weston");

        Customer savedCustomer = new Customer();
        savedCustomer.setFirstName(customerDTO.getFirstName());
        savedCustomer.setLastName(customerDTO.getLastName());
        savedCustomer.setId(1l);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDto = customerService.saveCustomerByDTO(1L, customerDTO);

        //then
        assertEquals(customerDTO.getFirstName(), savedDto.getFirstName());
        assertEquals("/api/v1/customers/1", savedDto.getCustomerUrl());
    }
	
	@Test
	public void deleteCustomerById() throws Exception {
		 Long id = 1L;
		 customerRepository.deleteById(id);
		 verify(customerRepository, times(1)).deleteById(anyLong());
		 
	}
}
