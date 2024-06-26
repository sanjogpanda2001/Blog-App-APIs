package com.sanjog.blog.services;

import java.util.List;

import com.sanjog.blog.payloads.CategoryDTO;

public interface CategoryService {
	public CategoryDTO createCategory(CategoryDTO categorydto);
	public CategoryDTO updateCategory(CategoryDTO categorydto, Integer categoryId);
	public void deleteCategory(Integer categoryId);
	public CategoryDTO getCatById(Integer categoryId);
	public List<CategoryDTO>getAll();

}
