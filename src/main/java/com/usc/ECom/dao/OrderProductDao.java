package com.usc.ECom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usc.ECom.beans.OrderProduct;
@Repository
public interface OrderProductDao extends JpaRepository<OrderProduct, Integer>{

}
