package com.usc.ECom.service;

import java.util.List;

//import java.util.ArrayList;
//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.usc.ECom.beans.Product;
//import com.usc.ECom.beans.UserProfile;
import com.usc.ECom.dao.ProductDao;
import com.usc.ECom.http.Response;
@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductDao productDao;
	
//    public Response findAll() { 
//   	 List<Product> productList = productDao.findAll();
//        if (CollectionUtils.isEmpty(productList)) {
//       	 return new Response(true,200, "But no Product is here!");
//        }
//        return new Response(true, 200, "");
//   }
	
	public Response getPorductById(int id) {
        if (!productDao.findById(id).isEmpty()) {
        	Product product=productDao.findById(id).orElseThrow(RuntimeException::new);//Type mismatch: cannot convert from Optional<Product> to Product
            return new Response(true,200,"");
        } else {
            return new Response(false,400, "Product is not found!");
        }		
	}
//	 Optional<Product> optionalProduct = productDao.findById(id);
//	    if (optionalProduct.isPresent()) {
//	        Product product = optionalProduct.get(); ...
//	        }
    public Response deleteProduct(int id) {
        if (!productDao.findById(id).isEmpty()) {
        	productDao.deleteById(id);
            return new Response(true);
        } else {
            return new Response(false, "Product is not found!");
        }
    }
    public Response AddNewProduct(Product product) {
//        System.out.println(product); //what if fields are null?
        productDao.save(product);
        return new Response(true);
    }

	public Response changeProduct(Product product) {
		Product productInDB = productDao.findById(product.getId()).orElseThrow(RuntimeException:: new);
		productInDB.setName(product.getName());
		productInDB.setBrand(product.getBrand());////////????????if Brand is null?
		productInDB.setImage(product.getImage());//????????
		productInDB.setPrice(product.getPrice());
		productInDB.setStock(product.getStock());
		productDao.save(productInDB);
		return new Response(true);
	}


}


//
//public Response<Product> getPorductById(int id) {
//    if (!productDao.findById(id).isEmpty()) {
//    	Product product=productDao.findById(id).orElseThrow(RuntimeException::new);//Type mismatch: cannot convert from Optional<Product> to Product
//        return new Response<Product>(true,200,"", product);
//    } else {
//        return new Response<>(false,400, "Product is not found!");
//    }		
//}