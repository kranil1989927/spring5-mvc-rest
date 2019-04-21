package learn.springframework.service;

import java.util.List;
import learn.springframework.model.CustomerDTO;

public interface CustomerService {

	List<CustomerDTO> getAllCustomers();

	CustomerDTO getCustomerById(Long id);

	CustomerDTO createNewCustomer(CustomerDTO customerDTO);
	
	CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO);
	
	CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);
	
	void deleteCustomerById(Long id);
}
