package com.Day5.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.Day5.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
