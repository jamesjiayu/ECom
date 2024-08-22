package com.usc.ECom.service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
//import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.usc.ECom.beans.User;
import com.usc.ECom.beans.UserProfile;
import com.usc.ECom.dao.UserDao;
import com.usc.ECom.http.Response;
@Service//put in IOC container
@Transactional //roll back if something wrong
public class UserService {

    @Autowired // 注入进来，然后使用
    UserDao userDao;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Response register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<UserProfile> profiles = new ArrayList<UserProfile>();
        profiles.add(new UserProfile(2));
        user.setProfiles(profiles);
        System.out.println(user);
        userDao.save(user);// return user.  <S extends T> S save(S entity); in CrudRepository<T, ID> 
        return new Response(true);
    }

    public Response registerAdm(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<UserProfile> profiles = new ArrayList<UserProfile>();
        profiles.add(new UserProfile(1));
        user.setProfiles(profiles);
        System.out.println(user);
        userDao.save(user);
        return new Response(true);
    }

    public Response changePassword(User user, Authentication authentication) {
        if (user.getUsername().equals(authentication.getName()) || isAdmin(authentication.getAuthorities())) {
            User u = userDao.findByUsername(user.getUsername());
            u.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.save(u);
            return new Response(true);
        } else {
            return new Response(false);
        }
    }

    public boolean isAdmin(Collection<? extends GrantedAuthority> profiles) {
        boolean isAdmin = false;
        for (GrantedAuthority profile : profiles) {
            if (profile.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
            }
        }
        return isAdmin;
    }

    public Response deleteUser(int id) {
        if (!userDao.findById(id).isEmpty()) {//NONO: userDao.findById(id) != null//userDao.findById(id).isPresent()
            userDao.deleteById(id);
            return new Response(true);
        } else {
            return new Response(false, "User is not found!");
        }
    }    
    
}
//public Response<List<User>> findAll() { //<List<User>>
//  	 List<User> userList = userDao.findAll();
//       if (CollectionUtils.isEmpty(userList)) {
//           //return List.of();
//      	 return new Response<>(true,200, "But no user is here!",null);
//  }
//       return new Response<>(true, 200, "", userList);//not good, should use data<T> in Response
//  }
	
//String result = userList.stream()
//.map(User::toString)
//.collect(Collectors.joining(" | "));
//System.out.println(result);