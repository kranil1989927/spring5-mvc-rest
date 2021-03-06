package learn.springframework.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import learn.springframework.api.v1.model.CategoryDTO;
import learn.springframework.api.v1.model.CategoryListDTO;
import learn.springframework.service.CategoryService;

@Controller
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {

	public static final String BASE_URL = "/api/v1/categories";
	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	public ResponseEntity<CategoryListDTO> getAllCategories() {
		return new ResponseEntity<CategoryListDTO>(
				new CategoryListDTO(this.categoryService.getAllCategories()), HttpStatus.OK);
	}

	@GetMapping("/{name}")
	public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
		return new ResponseEntity<CategoryDTO>(
				this.categoryService.getCategoryByName(name), HttpStatus.OK);
	}

}
