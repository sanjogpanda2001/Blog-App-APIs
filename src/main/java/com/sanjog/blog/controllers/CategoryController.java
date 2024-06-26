package com.sanjog.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanjog.blog.entities.Category;
import com.sanjog.blog.payloads.ApiResponse;
import com.sanjog.blog.payloads.CategoryDTO;
import com.sanjog.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryservice;
	
	@PostMapping("/")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categorydto){
		CategoryDTO c=this.categoryservice.createCategory(categorydto);
		return new ResponseEntity<CategoryDTO>(c,HttpStatus.CREATED);
	}
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categorydto, @PathVariable Integer id){
		CategoryDTO c=this.categoryservice.updateCategory(categorydto,id);
		return new ResponseEntity<CategoryDTO>(c,HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteMapping(@PathVariable Integer id){
		this.categoryservice.deleteCategory(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("deleted",true), HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> getById(@PathVariable Integer id){
		CategoryDTO c=this.categoryservice.getCatById(id);
		return new ResponseEntity<CategoryDTO>(c,HttpStatus.OK);
	}
	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getAll(){
		List<CategoryDTO> c=this.categoryservice.getAll();
		return new ResponseEntity<List<CategoryDTO>>(c,HttpStatus.OK);
	}

	
	
	
	

}
