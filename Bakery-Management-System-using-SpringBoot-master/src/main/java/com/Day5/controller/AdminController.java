package com.Day5.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Day5.dto.ProductDTO;
import com.Day5.model.Category;
import com.Day5.model.Product;
import com.Day5.service.CategoryService;
import com.Day5.service.ProductService;



@Controller
public class AdminController {
	
	public static String uploadDir= System.getProperty("user.dir")+"/src/main/resources/static/productImages";
	
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;

	@GetMapping("/admin")
	public String adminHome()
	{
		return "adminHome";
	}
	@GetMapping("/admin/categories")
	public String getCat(Model model)
	{
		model.addAttribute("categories",categoryService.getAllCategory());
		return "categories";
	}

	@PostMapping("/admin/categories/add")
	public String postCatAdd(@ModelAttribute("category") Category category)
	{
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCat(@PathVariable int id)
	{
		 categoryService.removeCategoryById(id);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/update/{id}")
	public String updateCat(@PathVariable int id, Model model)
	{
	 Category category=	categoryService.getCategoryById(id);
	 model.addAttribute("category",category);
	 return "categoriesAdd";
	}
	// product Section 
	
	@GetMapping("/admin/products")
	public String products(Model model) {
		model.addAttribute("products", productService.getAllProduct());
		return "products";
	}
	
	
	@GetMapping("/admin/products/add")
	public String productAddGet(Model model)
	{
		model.addAttribute("productDTO", new ProductDTO());
		model.addAttribute("categories", categoryService.getAllCategory());
		return "productsAdd";
	}
	
	@PostMapping("/admin/products/add")
	public String ProductAddPost(@ModelAttribute("productDTO")ProductDTO productDTO,
			@RequestParam("productImage") MultipartFile file, 
			@RequestParam("imgName") String imgName) throws IOException
	{
		
		Product product = new Product();
		product.setId(productDTO.getId());
		product.setName(productDTO.getName());
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()));
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		
		String imageUUID;
		if(!file.isEmpty())
		{
			imageUUID= file.getOriginalFilename();
			Path faleNameAndPath = Paths.get(uploadDir, imageUUID);
			Files.write(faleNameAndPath,file.getBytes());	
		}
		else
		{
			imageUUID=imgName;
		}
		
		product.setImageName(imageUUID);
		productService.addProduct(product);
		
		return "redirect:/admin/products";
	}
	
	@GetMapping("/admin/product/delete/{id}")
	public String deleteProduct(@PathVariable long id)
	{
		productService.removeProductById(id);
		return "redirect:/admin/products";
	}
	
	
	@GetMapping("/admin/product/update/{id}")
	public String updateProductGet(@PathVariable long id, Model model)
	{
		Product product = productService.getProductById(id);
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setCategoryId(product.getCategory().getId());
		productDTO.setPrice(product.getPrice());
		productDTO.setWeight(product.getWeight());
		productDTO.setDescription(product.getDescription());
		productDTO.setImageName(product.getImageName());
		
		
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("productDTO", productDTO);
		
		return "productsAdd";
	}
	
	
	
	
}
