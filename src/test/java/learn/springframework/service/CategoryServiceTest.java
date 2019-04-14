package learn.springframework.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import learn.springframework.api.v1.mapper.CategoryMapper;
import learn.springframework.api.v1.model.CategoryDTO;
import learn.springframework.domain.Category;
import learn.springframework.repositories.CategoryRepository;

public class CategoryServiceTest {

	public static final long ID = 2L;
	public static final String NAME = "Jimmy";

	CategoryService categoryService;

	@Mock
	CategoryRepository categoryRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		categoryService = new CategoryServiceImpl(CategoryMapper.INSTANCE, categoryRepository);
	}

	@Test
	public void getAllCategories() throws Exception {
		List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());

		when(categoryRepository.findAll()).thenReturn(categories);

		List<CategoryDTO> categoryDTO = categoryService.getAllCategories();

		assertEquals(3, categoryDTO.size());
	}

	@Test
	public void getCategoryByName() throws Exception {
		Category category = new Category();
		category.setId(ID);
		category.setName(NAME);

		when(categoryRepository.findByName(NAME)).thenReturn(category);

		CategoryDTO categoryDTO = categoryService.getCategoryByName(NAME);

		assertEquals(Long.valueOf(ID), Long.valueOf(categoryDTO.getId()));
		assertEquals(NAME, categoryDTO.getName());
	}

}
