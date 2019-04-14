package learn.springframework.bootstrap;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import learn.springframework.domain.Category;
import learn.springframework.domain.Customer;
import learn.springframework.repositories.CategoryRepository;
import learn.springframework.repositories.CustomerRepository;

@Component
public class Bootstrap implements CommandLineRunner {

	private CategoryRepository categoryRepository;
	private CustomerRepository customerRepository;

	public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		
		loadCustomers();

		loadCategories();
	}

	private void loadCategories() {
		String categoryType[] = { "Fruits", "Dried", "Fresh", "Exotic", "Nuts" };
		List<String> categories = Arrays.stream(categoryType).collect(Collectors.toList());

		categories.forEach(name -> {
			Category category = new Category();
			category.setName(name);

			categoryRepository.save(category);
		});

		System.out.println("Data Loaded : " + categoryRepository.count());
	}

	private void loadCustomers() {

		Customer customer1 = new Customer();
		customer1.setId(11L);
		customer1.setFirstName("Amit");
		customer1.setLastName("Kumar");
		this.customerRepository.save(customer1);

		Customer customer2 = new Customer();
		customer2.setId(12L);
		customer2.setFirstName("Rajesh");
		customer2.setLastName("Kumar");
		this.customerRepository.save(customer2);

		System.out.println("Customer Data Loaded : " + this.customerRepository.count());
	}
}
