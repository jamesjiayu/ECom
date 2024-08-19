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

import com.usc.ECom.beans.OrderProduct;
import com.usc.ECom.beans.Product;
import com.usc.ECom.dao.OrderProductDao;
import com.usc.ECom.http.Response;
import com.usc.ECom.service.OrderProductService;

@RestController
@RequestMapping("/order-product")
public class OrderProductController {
	@Autowired 
	private OrderProductService orderProductService;
	@Autowired 
	private OrderProductDao orderProductDao;
	@GetMapping
	public List<OrderProduct> getOrderProducts(){
		return orderProductDao.findAll();
	}
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
	@PostMapping
    public Response addOrderProduct(@RequestBody OrderProduct orderproduct) { 
        return orderProductService.AddNewOrderProduct(orderproduct); 
    }
	@DeleteMapping("/{id}")
    public Response deletOrderProduct(@PathVariable int id) {
    	return orderProductService.deleteOrderProduct(id);
    }
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
	@PutMapping
	public Response changeOrderProduct(@RequestBody OrderProduct orderProduct) {
    	return orderProductService.changeProduct(orderProduct);
    }
}
