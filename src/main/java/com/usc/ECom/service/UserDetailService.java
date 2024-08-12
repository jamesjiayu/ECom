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

	public Response	 deteleUserDetail(int id) {
		if(userDetailDao.findById(id).isPresent()) {
			userDetailDao.deleteById(id);
			return new Response(true);
		}else {
			return new Response(false, "The user's details are not find");
		}
	}
	
	public Response addUserDetail(UserDetail userDetail) {
		//email in DB is null && emails different: if emails same, return false 
		//!userDetailDao.findByEmail(userDetailInDB.getEmail()).equals(userDetail.getEmail())) 
		//userDetailDao.findById(userDetailInDB.getId()).isEmpty()
		UserDetail userDetailInDB= userDetailDao.findByEmail(userDetail.getEmail());
		if(userDetailInDB.getEmail().isEmpty()){ 
			userDetailDao.save(userDetail);
			return new Response(true);	
		}else {
			return new Response(false,"The user's email has already been registered.");
		}
		
	}
	
	public Response changeUserDetail(UserDetail userDetail) {
		UserDetail userDetailInDB= userDetailDao.findByEmail(userDetail.getEmail());
		if(userDetailInDB.getEmail().isEmpty()){ 
			//check if name is null or not???????? frontend should do it
			return new Response(false, "The user's email is not in the system");
		}else {
			userDetailDao.save(userDetail);
			return new Response(true);
		}
	}
}
