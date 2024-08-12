package com.usc.ECom.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usc.ECom.beans.Order;

public interface OrderDao extends JpaRepository<Order, Integer> {

}
