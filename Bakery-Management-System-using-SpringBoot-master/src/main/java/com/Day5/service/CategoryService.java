package com.Day5.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Day5.model.Category;
import com.Day5.repository.CategoryRepository;

@Service
public class CategoryService{

	@Autowired
	CategoryRepository categoryRepository;
	public List<Category> getAllCategory(){return categoryRepository.findAll();}
	
	
	public void addCategory(Category category){
		categoryRepository.save(category);}

	
	public Category getCategoryById(int id) {
		return categoryRepository.findById(id).get();}


	public void removeCategoryById(int id) {
		// TODO Auto-generated method stub
		
	}	
}
