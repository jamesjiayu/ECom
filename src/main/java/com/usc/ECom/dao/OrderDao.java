package com.usc.ECom.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usc.ECom.beans.Order;
import com.usc.ECom.beans.User;
@Repository
public interface OrderDao extends JpaRepository<Order, Integer> {


	List<Order> findByUserId(int userId);

}
