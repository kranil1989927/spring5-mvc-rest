package learn.springframework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import learn.springframework.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findByName(String name);

}
