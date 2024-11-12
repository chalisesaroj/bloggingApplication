package com.udemy.learn.blogging.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udemy.learn.blogging.payload.CategoryDto;
import com.udemy.learn.blogging.service.CategoryService;
@RestController
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	@PostMapping("")
	public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
		return categoryService.addCategory(categoryDto);
	}
	@GetMapping("")
	public List<CategoryDto> getAllCategory() {
		return categoryService.getAllCategory();
	}
	@GetMapping("getCategoryNames")
	public List<String> getCategorynames(){
		return categoryService.getCategorynames();
	
	}
}
