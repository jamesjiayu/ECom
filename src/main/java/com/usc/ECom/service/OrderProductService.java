package com.usc.ECom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usc.ECom.beans.OrderProduct;
import com.usc.ECom.beans.Product;
import com.usc.ECom.dao.OrderProductDao;
import com.usc.ECom.http.Response;

@Service
@Transactional
public class OrderProductService {
	@Autowired OrderProductDao orderProductDao;

	public Response AddNewOrderProduct(OrderProduct orderProduct) {
		orderProductDao.save(orderProduct);
        return new Response(true);
	}

	public Response deleteOrderProduct(int id) {
        if (!orderProductDao.findById(id).isEmpty()) {
        	orderProductDao.deleteById(id);
            return new Response(true);
        } else {
            return new Response(false, "Product is not found!");
        }
	}

	public Response changeProduct(OrderProduct orderProduct) {
		OrderProduct OrderProductInDB = orderProductDao.findById(orderProduct.getId()).orElseThrow();
		OrderProductInDB.setQuantity(orderProduct.getQuantity());//product s gone,if the quantity is 0.
		//change the product? if change it , it's a new OP orderProduct.
		orderProductDao.save(OrderProductInDB);
		return new Response(true);
	}

}
