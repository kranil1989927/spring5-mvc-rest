package learn.springframework.api.v1.mapper;

import static org.junit.Assert.*;

import org.junit.Test;

import learn.springframework.api.v1.mapper.CustomerMapper;
import learn.springframework.api.v1.model.CustomerDTO;
import learn.springframework.domain.Customer;

public class CustomerMapperTest {

	public static final long ID = 1L;
	public static final String FIRST_NAME = "Anil";
	public static final String LAST_NAME = "Kumar";

	CustomerMapper customerMapper = CustomerMapper.INSTANCE;

	@Test
	public void customerToCustomerDTO() {

		// given
		Customer customer = new Customer();
		customer.setId(ID);
		customer.setFirstName(FIRST_NAME);
		customer.setLastName(LAST_NAME);

		// when
		CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

		// then
		assertEquals(FIRST_NAME, customerDTO.getFirstName());
		assertEquals(LAST_NAME, customerDTO.getLastName());

	}

}
