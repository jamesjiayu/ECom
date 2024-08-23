package com.usc.ECom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.usc.ECom.beans.User;
import com.usc.ECom.beans.UserDetail;
import com.usc.ECom.dao.UserDao;
import com.usc.ECom.dao.UserDetailDao;
import com.usc.ECom.http.Response;

@Service
@Transactional
public class UserDetailService {
	@Autowired
	private UserDetailDao userDetailDao;
	@Autowired
	private UserDao userDao;
//	@Autowired
//	private User user;

	public Response	 deteleUserDetail(int id) {
		if(userDetailDao.findById(id).isPresent()) {
			userDetailDao.deleteById(id);
			return new Response(true);
		}else {
			return new Response(false, "The user's details are not find");
		}
	}
	
	public Response addUserDetail(UserDetail userDetail, Authentication authentication) {
		//email in DB is null && emails different: if emails same, return false 
		//!userDetailDao.findByEmail(userDetailInDB.getEmail()).equals(userDetail.getEmail())) 
		//userDetailDao.findById(userDetailInDB.getId()).isEmpty()
		UserDetail userDetailInDB= userDetailDao.findByEmail(userDetail.getEmail());
		if(userDetailInDB==null){ 
			userDetail.setUser(userDao.findByUsername( authentication.getName() ));
			userDetailDao.save(userDetail);
			return new Response(true);	
		}else {
			return new Response(false,"The user has already been registered: The same email found ");
		}
		
	}
	
	public Response changeUserDetail(UserDetail userDetail , Authentication authentication ) { //NoSuchElementException
		//user=	userDao.findByUsername( authentication.getName() );
		//UserDetail userDetailInDB=userDetailDao.findByUser(userDao.findByUsername( authentication.getName() ));
		UserDetail userDetailInDB= userDetailDao.findById(userDetail.getId()).get(); //findByEmail(userDetail.getEmail());	    
		if(userDetailInDB==null){ //userDetailInDB.getEmail().isEmpty() NullPointerException
			//check if name is null or not???????? frontend should do it
			return new Response(false, "No such user");// : the user's email is not in the system
		}else {
			userDetail.setUser(userDao.findByUsername( authentication.getName() ));
			userDetailDao.save(userDetail);
			return new Response(true);
		}
	}
}
