package com.usc.ECom.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.usc.ECom.beans.User;
import com.usc.ECom.dao.UserDao;
import com.usc.ECom.http.Response;
import com.usc.ECom.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;//?never used?
/* in SecurityConfig
 * ...hasAnyRole("ROLE_ADMIN").and()
            .exceptionHandling().authenticationEntryPoint(authenticationEntryPointImpl).and().exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandlerImpl).and()
 * */
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")// check authenticationEntryPointImpl, then the AccessDeniedHandlerImpl? just a @PreAuthorize!
    public List<User> getUsers() {
    	return userDao.findAll(); //service then dao
    }
//    public Response getUsers() {
//    	return userService.findAll();
//    }    

    @PostMapping ////login first? and preauthorize. or everyone can do it. same as delete
    public Response addUser(@RequestBody User user) { //Json
        return userService.register(user); //registerAdm(user)
    }

    @PutMapping //("/{id}") //who change it?// only can change it by himself?//
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public Response changeUser(@RequestBody User user, Authentication authentication) {
        return userService.changePassword(user, authentication);
    }

    @DeleteMapping("/{id}") //login first? and preauthorize. or everyone can do it.
    public Response deletUser(@PathVariable int id) {
    	return userService.deleteUser(id);
    }
    
    
    
}
//@PutMapping("/student/{id}")//in postman, from-data, //or Params Tab(chatgpt)
//public Response<StudentDTO> updateStudentById(@PathVariable long id, @RequestParam(required = false) String name,
//                                            @RequestParam(required = false) String email) {
//
//  return Response.newSuccess(studentService.updateStudentById(id, name, email));
//}
