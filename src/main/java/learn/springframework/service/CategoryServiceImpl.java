package learn.springframework.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import learn.springframework.api.v1.mapper.CategoryMapper;
import learn.springframework.api.v1.model.CategoryDTO;
import learn.springframework.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryMapper categoryMapper;
	private final CategoryRepository categoryRepository;

	public CategoryServiceImpl(CategoryMapper categoryMapper, CategoryRepository categoryRepository) {
		this.categoryMapper = categoryMapper;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		return this.categoryRepository.findAll()
				.stream()
				.map(categoryMapper::categoryToCategoryDTO)
				.collect(Collectors.toList());
	}

	@Override
	public CategoryDTO getCategoryByName(String name) {
		return this.categoryMapper.categoryToCategoryDTO(this.categoryRepository.findByName(name));
	}

}
