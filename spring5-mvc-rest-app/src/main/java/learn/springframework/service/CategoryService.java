package learn.springframework.service;

import java.util.List;

import learn.springframework.api.v1.model.CategoryDTO;

public interface CategoryService {

	List<CategoryDTO> getAllCategories();

	CategoryDTO getCategoryByName(String name);
}
