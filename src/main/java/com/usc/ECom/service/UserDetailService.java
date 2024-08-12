package com.usc.ECom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usc.ECom.beans.UserDetail;
import com.usc.ECom.dao.UserDetailDao;
import com.usc.ECom.http.Response;

@Service
@Transactional
public class UserDetailService {
	@Autowired
	private UserDetailDao userDetailDao;
	@Autowired
	private UserDetail userDetail;
	
	public Response	 deteleUserDetail(int id) {
		if(userDetailDao.findById(id).isPresent()) {
			userDetailDao.deleteById(id);
			return new Response(true);
		}else {
			return new Response(false, "The user's details are not find");
		}
	}
	
	public Response addUserDetail(UserDetail userDetail) {
//how to find the user in user table and put user_id in here?
		userDetailDao.save(userDetail);
		return new Response(true);
	}
	public Response changeUserDetail(UserDetail userDetail) {
		//if(userDetail.getEmail(),equals(userDetail))
		return new Response(true);
		
	}
}
