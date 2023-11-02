package com.vaibhavtraders.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaibhavtraders.dao.ProductRepository;
import com.vaibhavtraders.entity.Product;
import com.vaibhavtraders.exception.GeneralException;

@Component
public class ProductBO {
	
	@Autowired
	ProductRepository productRepository;
	
	public Product insert(Product p) throws GeneralException {
		try {
			if(p.getName() == null) {
				throw new GeneralException("Product Name cannot be empty.");
			}
			if(p.getProductType() == null) {
				throw new GeneralException("Product Type cannot be empty.");
			}
		} catch (GeneralException e) {
			throw new GeneralException("Error while inserting product.");
		}
		return productRepository.save(p);
	}
	
	public Product findProduct(Long productId) throws GeneralException {
		try {
			if(productId == null) {
				throw new GeneralException("Product ID not supplied.");
			}
		} catch (GeneralException e) {
			throw new GeneralException("Error while finding particular product.");
		}
		return productRepository.findById(productId).orElse(null);
	}
	
	public List<Product> findAllProducts() throws GeneralException {
		List<Product> allProductsList = new ArrayList<Product>();
		try {
			allProductsList = productRepository.findAll();
			if(allProductsList == null) {
				throw new GeneralException("No Products Found.");
			}
		} catch (GeneralException e) {
			throw new GeneralException("Error while fetching Products list.");
		}
		return allProductsList;
	}
	
	public Product deleteProduct(Product p) throws GeneralException {
		try {
			if(p == null) {
				throw new GeneralException("Param id Product p is null.");
			}
		} catch (GeneralException e) {
			throw new GeneralException("Error while deleting Specific Product.");
		}
		productRepository.delete(p);
		System.out.println("Product with ID : " + p.getProductID() + " deleted successfully.");
		return p;
	}
}
