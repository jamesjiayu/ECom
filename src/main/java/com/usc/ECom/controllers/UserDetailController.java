package com.usc.ECom.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usc.ECom.beans.UserDetail;
import com.usc.ECom.dao.UserDetailDao;
import com.usc.ECom.http.Response;
import com.usc.ECom.service.UserDetailService;
@RestController
@RequestMapping("/user-details")
public class UserDetailController {
	@Autowired
	private UserDetailDao userDetailDao;
	@Autowired
	private UserDetailService userDetailService;
	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<UserDetail> findAll() {
		return userDetailDao.findAll();
	}
	@DeleteMapping("/{id}")
	public Response deleteUserDetail(@PathVariable int id	) {
		return userDetailService.deteleUserDetail(id);
	}
	@PostMapping
	public Response addUserDetail(@RequestBody UserDetail userDetail) {
		return userDetailService.addUserDetail(userDetail);
	}
	@PutMapping
	@PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
	public Response changeUserDetail(UserDetail userDetail) {
		return userDetailService.changeUserDetail(userDetail);
	}
	
}
