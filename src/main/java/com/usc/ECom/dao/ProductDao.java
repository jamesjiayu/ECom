package com.usc.ECom.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.usc.ECom.beans.Product;

//JpaSpecificationExecutor<Product> // find支持的关键字， And , Between, In, StaringWith? List<Student> findByNameStartingWith(String namePrefix);
@Repository
public interface ProductDao extends JpaRepository<Product, Integer> {

	Product findByName(String name); 

}
//