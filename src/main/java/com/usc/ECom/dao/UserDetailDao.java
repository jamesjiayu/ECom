package com.usc.ECom.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usc.ECom.beans.User;
import com.usc.ECom.beans.UserDetail;
@Repository
public interface UserDetailDao extends JpaRepository<UserDetail, Integer> {

	UserDetail findByEmail(String email);

	UserDetail findByUser(User user);

}
