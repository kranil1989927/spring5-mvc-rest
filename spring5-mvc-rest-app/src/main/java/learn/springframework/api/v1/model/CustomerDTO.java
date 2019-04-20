package learn.springframework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerDTO {

	private String firstName;
	private String lastName;
	
	@JsonProperty("customer_url")
	private String customerUrl;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCustomerUrl() {
		return customerUrl;
	}

	public void setCustomerUrl(String customerUrl) {
		this.customerUrl = customerUrl;
	}

}
