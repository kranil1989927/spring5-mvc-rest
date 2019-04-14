package learn.springframework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import learn.springframework.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
