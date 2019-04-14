package learn.springframework.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import learn.springframework.api.v1.mapper.CustomerMapper;
import learn.springframework.api.v1.model.CustomerDTO;
import learn.springframework.controllers.v1.CustomerController;
import learn.springframework.domain.Customer;
import learn.springframework.repositories.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerMapper customerMapper;
	private final CustomerRepository customerRepository;

	public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
		this.customerMapper = customerMapper;
		this.customerRepository = customerRepository;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		return customerRepository.findAll().stream().map(customer -> {
			CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
			customerDTO.setCustomerUrl(getCustomerUrl(customer.getId()));
			return customerDTO;
		}).collect(Collectors.toList());
	}

	@Override
	public CustomerDTO getCustomerById(Long id) {
		return customerRepository.findById(id)
				.map(customerMapper::customerToCustomerDTO)
				.map(customerDTO -> {
                    //set API URL
                    customerDTO.setCustomerUrl(getCustomerUrl(id));
                    return customerDTO;
                })
				.orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {

		Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
		return saveAndReturnDTO(customer);
	}

	private CustomerDTO saveAndReturnDTO(Customer customer) {
		Customer savedCustomer = customerRepository.save(customer);

		CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(savedCustomer);
		returnDTO.setCustomerUrl(getCustomerUrl(savedCustomer.getId()));

		return returnDTO;
	}

	@Override
	public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
		Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        customer.setId(id);
		return saveAndReturnDTO(customer);
	}

	@Override
	public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
		return customerRepository.findById(id).map(customer -> {
			if (customerDTO.getFirstName() != null) {
				customer.setFirstName(customerDTO.getFirstName());
			}

			if (customerDTO.getLastName() != null) {
				customer.setLastName(customerDTO.getLastName());
			}

			CustomerDTO returnDto = customerMapper.customerToCustomerDTO(customerRepository.save(customer));
			returnDto.setCustomerUrl(getCustomerUrl(id));
			return returnDto;
		}).orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public void deleteCustomerById(Long id) {
		customerRepository.deleteById(id);
	}
	
	private String getCustomerUrl(Long id) {
		return CustomerController.BASE_URL + "/" + id;
	}

}
