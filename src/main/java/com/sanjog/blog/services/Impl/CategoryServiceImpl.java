package com.sanjog.blog.services.Impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanjog.blog.entities.Category;
import com.sanjog.blog.exceptions.ResourceNotFoundException;
import com.sanjog.blog.payloads.CategoryDTO;
import com.sanjog.blog.repositories.CategoryRepo;
import com.sanjog.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryrepo;
	
	@Autowired
	private ModelMapper modelmapper;

	@Override
	public CategoryDTO createCategory(CategoryDTO categorydto) {
		// TODO Auto-generated method stub
		Category c=this.modelmapper.map(categorydto,Category.class);
		Category createdCat=this.categoryrepo.save(c);
		return this.modelmapper.map(createdCat, CategoryDTO.class);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO categorydto, Integer categoryId) {
		// TODO Auto-generated method stub
		Category c=this.categoryrepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category","category id",categoryId));
		c.setCategoryTitle(categorydto.getCategoryTitle());
		c.setCategoryDesc(categorydto.getCategoryDesc());
		Category updatedcat=this.categoryrepo.save(c);
		return this.modelmapper.map(updatedcat, CategoryDTO.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category c=this.categoryrepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category","category id",categoryId));
		this.categoryrepo.delete(c);
		

	}

	@Override
	public CategoryDTO getCatById(Integer categoryId) {
		// TODO Auto-generated method stub
		Category c=this.categoryrepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category","category id",categoryId));
		
		return this.modelmapper.map(c, CategoryDTO.class);
	}

	@Override
	public List<CategoryDTO> getAll() {
		// TODO Auto-generated method stub
		List<Category> list=this.categoryrepo.findAll();
		List<CategoryDTO>l=new ArrayList<>();
		for(Category c: list) {
			l.add(this.modelmapper.map(c, CategoryDTO.class));
		}
		return l;
	}

}
