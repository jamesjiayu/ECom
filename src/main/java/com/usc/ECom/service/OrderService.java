package com.usc.ECom.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usc.ECom.beans.Order;
import com.usc.ECom.beans.OrderProduct;
import com.usc.ECom.beans.Product;
import com.usc.ECom.dao.OrderDao;
import com.usc.ECom.dao.OrderProductDao;
import com.usc.ECom.dao.ProductDao;
import com.usc.ECom.dao.UserDao;
import com.usc.ECom.http.Response;
import com.usc.ECom.security.SecurityUtils;

@Service
@Transactional
public class OrderService {
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private OrderProductDao orderProductDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ProductDao productDao;	
	@Autowired 
 	private OrderProductService orderProductService;

	public Response addOrder(Order order, Authentication authentication) {
		try {
			List<OrderProduct> purchases=order.getPurchases();
			purchases.forEach((orderProduct)-> {
				Product product= (Product) productDao.findById(orderProduct.getProduct().getId()).get();
				//productDao.findById(id).orElseThrow(RuntimeException::new);
				orderProduct.setProduct(product);
				orderProduct.setOrder(order);
			});
			order.setUser(userDao.findByUsername(authentication.getName()));//Auth is a interface, and no getName in itself, but should be in extends
			orderDao.save(order);//only save(order), OP table change automatically.
			return new Response(true);
		} catch (Exception e) {
			return new Response(false);
		}
	}
	public Response editOrder(Order order,Authentication authentication) {		
		try {
			List<OrderProduct> purchases=order.getPurchases();// if purchases is []?// front just delete the order, back no need to worry
			Order orderInDB = orderDao.findById(order.getId()).orElseThrow(RuntimeException:: new);			
			List<OrderProduct> purchasesToRemove= orderInDB.getPurchases();//can i remove all in DB first? but tables affected and performance bad
			List<OrderProduct> toRemove = purchasesToRemove.stream()
			        .filter(op -> !purchases.contains(op))
			        .collect(Collectors.toList());			
			if(!purchases.isEmpty()) {
				purchases.forEach(orderProduct->{
					Product product= productDao.findById(orderProduct.getProduct().getId()).orElseThrow(RuntimeException:: new);
					orderProduct.setProduct(product);
					orderProduct.setOrder(order);	// no need to do it, right?
					//orderProduct.setQuantity(orderProduct.getQuantity());	// no need to do it, right?//if qty is 0, front shouldn't pass the OP
				});
				
			}
			if(!toRemove.isEmpty()) {
				orderProductService.deleteOrderProducts(toRemove);//after deleting, list<OP> in order is []????? what's gonna happen to Order? is it ok?	
			}
			order.setUser(userDao.findByUsername(authentication.getName()));//auth is not Basic auth which I put into, its the JSession's relation to
			orderDao.save(order);
			
			return new Response(true);
		} catch (Exception e) {
			//log4j
			return new Response(false);
		}
	}
	
//	if(orderDao.findById(orderInDB.getId()).isPresent()) {
	public Order findById(int id) {
		return (Order)orderDao.findById(id).get();
	}


	public List<Order> getOrders(Authentication authentication) {
		if(SecurityUtils.isAdmin(authentication.getAuthorities())){
			return (List<Order>)orderDao.findAll();//why (List<Order>) ? return null?or sth else?
		}
		return orderDao.findByUserId(userDao.findByUsername(authentication.getName()).getId());
	}
	
	public Response	deleteOrder(int id) {
		if(orderDao.findById(id).isPresent()) {
			orderDao.deleteById(id);
			return new Response(true);
		}else {
			return new Response(false,"The Order is not found.");
		}	
		
	}
	
	

}
