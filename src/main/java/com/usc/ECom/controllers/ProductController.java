package com.usc.ECom.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usc.ECom.beans.Product;
import com.usc.ECom.dao.ProductDao;
import com.usc.ECom.http.Response;
import com.usc.ECom.service.ProductService;

@RestController
//@RequestMapping("/product")
public class ProductController {
	@Autowired 
	private ProductService productService;
	
	@Autowired 
	private ProductDao productDao;
	
	@GetMapping("/product/{id}")
	public Response getProductById(@PathVariable int id) {
		return productService.getPorductById(id);
	}
	@GetMapping("/product")
	public List<Product> getProducts(){
		return productDao.findAll();
	}
//    public Response getProducts() {
//    	return productService.findAll();
//    }
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PostMapping("/product")
    public Response addProduct(@RequestBody Product product) { 
        return productService.AddNewProduct(product); 
    }
    @DeleteMapping("/product/{id}")
    public Response deletProduct(@PathVariable int id) {
    	return productService.deleteProduct(id);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@PutMapping("/product")
	public Response changeProduct(@RequestBody Product product) {
    	return productService.changeProduct(product);
    }
}
