package learn.springframework.controllers.v1;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import learn.springframework.api.v1.model.CustomerDTO;
import learn.springframework.controllers.RestResponseEntityExceptionHandler;
import learn.springframework.controllers.v1.CustomerController;
import learn.springframework.service.CustomerService;
import learn.springframework.service.ResourceNotFoundException;

public class CustomerControllerTest extends AbstractRestControllerTest {

	@Mock
	CustomerService customerService;

	@InjectMocks
	CustomerController customerController;

	MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(customerController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler())
				.build();
	}

	@Test
	public void testListCustomers() throws Exception {
		// given
		CustomerDTO customer1 = new CustomerDTO();
		customer1.setFirstName("Amit");
		customer1.setLastName("Kumar");
		customer1.setCustomerUrl(CustomerController.BASE_URL + "/1");

		CustomerDTO customer2 = new CustomerDTO();
		customer2.setFirstName("Rajesh");
		customer2.setLastName("Kumar");
		customer2.setCustomerUrl(CustomerController.BASE_URL + "/2");

		when(customerService.getAllCustomers()).thenReturn(Arrays.asList(customer1, customer2));

		mockMvc.perform(get(CustomerController.BASE_URL)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.customers", hasSize(2)));
	}

	@Test
    public void testGetCustomerById() throws Exception {
		//given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setFirstName("Amit");
        customer1.setLastName("Kumar");
		customer1.setCustomerUrl(CustomerController.BASE_URL + "/1");
        
        when(customerService.getCustomerById(anyLong())).thenReturn(customer1);
        
		mockMvc.perform(get(CustomerController.BASE_URL + "/1")
				.accept(MediaType.APPLICATION_JSON)
        		.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName", equalTo("Amit")));
	}
	
	@Test
    public void createNewCustomer() throws Exception {
        //given
        CustomerDTO customerDto = new CustomerDTO();
        customerDto.setFirstName("Fred");
        customerDto.setLastName("Flintstone");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customerDto.getFirstName());
        returnDTO.setLastName(customerDto.getLastName());
		returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.createNewCustomer(customerDto)).thenReturn(returnDTO);
        
        mockMvc.perform(post(CustomerController.BASE_URL)
        		.contentType(MediaType.APPLICATION_JSON)
        		.content(asJsonString(customerDto)))
        		.andExpect(status().isCreated());
	}
	
	@Test
    public void testUpdateCustomer() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Fred");
        customer.setLastName("Flintstone");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName(customer.getLastName());
		returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //when/then
		mockMvc.perform(put(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Fred")))
                .andExpect(jsonPath("$.lastName", equalTo("Flintstone")))
				.andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }
	
	@Test
    public void testPatchCustomer() throws Exception {

        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Fred");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setFirstName(customer.getFirstName());
        returnDTO.setLastName("Flintstone");
		returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

		mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
				.accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo("Fred")))
                .andExpect(jsonPath("$.lastName", equalTo("Flintstone")))
				.andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
    }
	
	@Test
	public void testDeleteCustomer() throws Exception {
		mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		verify(customerService).deleteCustomerById(anyLong());
	}

	@Test
	public void testNotFoundException() throws Exception {

		when(customerService.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);

		mockMvc.perform(get(CustomerController.BASE_URL + "/222")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
