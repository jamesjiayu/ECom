package com.usc.ECom.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.usc.ECom.beans.User;


@Repository
public interface UserDao extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
