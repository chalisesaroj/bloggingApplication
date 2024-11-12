package com.udemy.learn.blogging.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udemy.learn.blogging.entity.Category;
import com.udemy.learn.blogging.payload.CategoryDto;
import com.udemy.learn.blogging.repository.CategoryRepository;
import com.udemy.learn.blogging.service.CategoryService;
import com.udemy.learn.blogging.service.PostService;
@Service
public class CategoryServiceImplementation  implements CategoryService{
@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		Category category=mapToEntity(categoryDto);
		 Category newCategory=categoryRepository.save(category);
		return maptoDto(newCategory);
	}
	@Override
	public CategoryDto maptoDto(Category category) {
		CategoryDto categoryDto=new CategoryDto();
		categoryDto.setId(category.getId());
		categoryDto.setDescription(category.getDescription());
		categoryDto.setName(category.getName());
	
		return categoryDto;
		
	}
	@Override
	public List<String> getCategorynames(){
		List<String>categoryList=categoryRepository.findAll().stream().map((category)->category.getName()).collect(Collectors.toList());
			return categoryList;
		}
	Category mapToEntity(CategoryDto categoryDto) {
		Category category=new Category();
		category.setDescription(categoryDto.getDescription());
		category.setId(categoryDto.getId());
		category.setName(categoryDto.getName());
		
		return category;
		
	}
	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category>listOfCategory=categoryRepository.findAll();
		return listOfCategory.stream().map(p->this.maptoDto(p)).collect(Collectors.toList());
	}

}
