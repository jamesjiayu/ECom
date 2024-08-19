package com.usc.ECom.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usc.ECom.beans.OrderProduct;

public interface OrderProductDao extends JpaRepository<OrderProduct, Integer>{

}
