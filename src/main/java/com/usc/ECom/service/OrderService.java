package com.usc.ECom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usc.ECom.beans.Order;
import com.usc.ECom.dao.OrderDao;
import com.usc.ECom.http.Response;

@Service
@Transactional
public class OrderService {
	@Autowired
	private OrderDao orderDao;
	
	public Response addOrder(Order order) {
		//??????????????????????????/ lot of tables!!!!!
		return new Response(true);
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
