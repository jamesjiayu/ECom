package com.usc.ECom.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usc.ECom.beans.Order;
import com.usc.ECom.dao.OrderDao;
import com.usc.ECom.http.Response;
import com.usc.ECom.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
//	@Autowired
//	private	OrderDao orderDao;
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/{id}")
	public Order getOrder(@PathVariable int id){
		return orderService.findById(id);//.get(); 
	}
	@GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
	public List<Order> getOrders(Authentication authentication){
	return orderService.getOrders(authentication);
	}
//	public List<Order> getOrders(){ 
//	return orderDao.findAll(); 
//}
// find all a user's orders? a user have many orders
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
	@DeleteMapping("/{id}")
	public Response deleteOrder(@PathVariable int id) {
		return orderService.deleteOrder(id);
	}
	
	@PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
	public Response addOrder(@RequestBody Order order, Authentication authentication) {
		return orderService.addOrder(order,authentication);
	}
	
	@PutMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
	public Response editOrder(@RequestBody Order order) {
		return orderService.editOrder(order);
	}

}
