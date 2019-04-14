package learn.springframework.api.v1.model;

import java.util.List;

public class CategoryListDTO {
	
	private List<CategoryDTO> categories;

	public CategoryListDTO(List<CategoryDTO> categories) {
		this.categories = categories;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryDTO> categories) {
		this.categories = categories;
	}
}
