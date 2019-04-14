package learn.springframework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import learn.springframework.domain.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {

}
