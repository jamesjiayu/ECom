package com.usc.ECom.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usc.ECom.beans.UserDetail;

public interface UserDetailDao extends JpaRepository<UserDetail, Integer> {

	UserDetail findByEmail(String email);

}
