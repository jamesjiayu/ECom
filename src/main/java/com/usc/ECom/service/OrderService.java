package com.usc.ECom.service;

import java.util.List;

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
	public void deleteOrderProducts (List<OrderProduct> purchases) {
		orderProductDao.deleteAll(purchases);
	}
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
			orderDao.save(order);
			return new Response(true);
		} catch (Exception e) {
			return new Response(false);
		}
	}
	
	public Response	deleteOrder(int id) {
		if(orderDao.findById(id).isPresent()) {
			orderDao.deleteById(id);
			return new Response(true);
		}else {
			return new Response(false,"The Order is not found.");
		}	
		
	}
	
	public Response editOrder(Order order) {
		
		try {
			Order orderInDB = orderDao.findById(order.getId()).orElseThrow(RuntimeException:: new);
			List<OrderProduct> purchasesToRemove= orderInDB.getPurchases();
			List<OrderProduct> purchases=order.getPurchases();

			orderDao.save(order);
			return new Response(true);
		} catch (Exception e) {
			//log4j
			return new Response(false);
		}
	}
	
	//????frontend gave the id?
//	if(orderDao.findById(orderInDB.getId()).isPresent()) {

	public Order findById(int id) {
		return (Order)orderDao.findById(id).get();//orElseThrow();
	}


	public List<Order> getOrders(Authentication authentication) {
		//if(SecurityUtils.isAdmin())//?????????????????????????? isAdmin()!!!!!
		return null;
	}

}
