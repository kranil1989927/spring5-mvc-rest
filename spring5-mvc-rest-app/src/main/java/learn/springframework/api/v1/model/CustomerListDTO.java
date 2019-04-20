package learn.springframework.api.v1.model;

import java.util.List;

public class CustomerListDTO {
	private List<CustomerDTO> customers;

	public CustomerListDTO(List<CustomerDTO> customers) {
		this.customers = customers;
	}

	public List<CustomerDTO> getCustomers() {
		return customers;
	}

	public void setCustomers(List<CustomerDTO> customers) {
		this.customers = customers;
	}

}
