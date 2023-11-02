package com.vaibhavtraders.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.vaibhavtraders.bo.ProductBO;
import com.vaibhavtraders.dto.ProductDTO;
import com.vaibhavtraders.entity.Product;
import com.vaibhavtraders.exception.GeneralException;
import com.vaibhavtraders.response.ResponseObject;

@Service
public class ProductService {
	
	@Autowired
	ProductBO productBO;
	
	static Logger logger = Logger.getLogger(ProductService.class);
	
	public ResponseObject createProduct(@RequestBody ProductDTO productDTO) throws GeneralException{
		logger.info("Create product method triggered inside Service Layer");
		logger.info("Before updating in the table : " + productDTO);
		ResponseObject responseObject = new ResponseObject();
		try {
			Product p = new Product();
			p.setName(productDTO.getName());
			p.setProductDescription(productDTO.getProductDescription());
			p.setHsnHacCode(productDTO.getHsnHacCode());
			p.setUnitOfMeasurement(productDTO.getUnitOfMeasurement());
			p.setStock(productDTO.getStock());
			p.setProductType(productDTO.getProductType());
			p.setGstPercentage(productDTO.getGstPercentage());
			p.setCessPercentage(productDTO.getCessPercentage());
			p.setCessAmount(productDTO.getCessAmount());
			p.setSellPrice(productDTO.getSellPrice());
			p.setSellPriceIncludingTax(productDTO.getSellPriceIncludingTax());
			p.setPurchasePrice(productDTO.getPurchasePrice());
			p.setPurchasePriceIncludingTax(productDTO.getPurchasePriceIncludingTax());
			p.setProductImage(productDTO.getProductImage());
			Product product = productBO.insert(p);
			
			if(product != null) {
				productDTO.setProductID(product.getProductID());
				productDTO.setName(product.getName());
				productDTO.setProductDescription(product.getProductDescription());
				productDTO.setHsnHacCode(product.getHsnHacCode());
				productDTO.setUnitOfMeasurement(product.getUnitOfMeasurement());
				productDTO.setStock(product.getStock());
				productDTO.setProductType(product.getProductType());
				productDTO.setGstPercentage(product.getGstPercentage());
				productDTO.setCessPercentage(product.getCessPercentage());
				productDTO.setCessAmount(product.getCessAmount());
				productDTO.setSellPrice(product.getSellPrice());
				productDTO.setSellPriceIncludingTax(product.getSellPriceIncludingTax());
				productDTO.setPurchasePrice(product.getPurchasePrice());
				productDTO.setPurchasePriceIncludingTax(product.getPurchasePriceIncludingTax());
				productDTO.setProductImage(product.getProductImage());
				responseObject.setProductDTO(productDTO);
				responseObject.setSuccessMessage("Created product successfully!");
				//all sysouts. skipping for now.
			}
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while creating product.");
			logger.error(e);
		}
		return responseObject;
	}
	
	public ResponseObject findProduct(Long productId) throws GeneralException {
		logger.info("Find product method triggered.");
		ResponseObject responseObject = new ResponseObject();
		try {
			if(productId == null) {
				responseObject.setFailureMessage("Cannot fetch the particular product. Please check Product ID.");
				return responseObject;
			}
			Product prod = productBO.findProduct(productId);
			if(prod != null) {
				responseObject.setSuccessMessage("Fetched the product successfully.");
				responseObject.setProduct(prod);
			}
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while finding the product inside ProductService.");
			logger.error(e);
		}
		return responseObject;
	}

	public ResponseObject findAllProducts() throws GeneralException {
		logger.info("Find all products method triggered.");
		ResponseObject responseObject = new ResponseObject();
		try {
			List<Product> productList = productBO.findAllProducts();
			List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
			if(productList != null) {
				responseObject.setSuccessMessage("Fetching all Products List successful.");
				for(Product product : productList) {
					ProductDTO productDTO = new ProductDTO();
					productDTO.setProductID(product.getProductID());
					productDTO.setName(product.getName());
					productDTO.setProductDescription(product.getProductDescription());
					productDTO.setHsnHacCode(product.getHsnHacCode());
					productDTO.setUnitOfMeasurement(product.getUnitOfMeasurement());
					productDTO.setStock(product.getStock());
					productDTO.setProductType(product.getProductType());
					productDTO.setGstPercentage(product.getGstPercentage());
					productDTO.setCessPercentage(product.getCessPercentage());
					productDTO.setCessAmount(product.getCessAmount());
					productDTO.setSellPrice(product.getSellPrice());
					productDTO.setSellPriceIncludingTax(product.getSellPriceIncludingTax());
					productDTO.setPurchasePrice(product.getPurchasePrice());
					productDTO.setPurchasePriceIncludingTax(product.getPurchasePriceIncludingTax());
					productDTO.setProductImage(product.getProductImage());
					productDTOList.add(productDTO);
					responseObject.setProductDTOList(productDTOList);
					System.out.println(productDTOList);
				}
			} else {
				responseObject.setFailureMessage("Error while fetching all Products.");
			}
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while fetching all Products. Contact Admin.");
			logger.error(e);
		}
		return responseObject;
	}
	
	public ResponseObject deleteProduct(@RequestBody ProductDTO productDTO) throws GeneralException {
		logger.info("Delete product method triggered.");
		ResponseObject responseObject = new ResponseObject();
		try {
			Product product = new Product();
			product.setProductID(productDTO.getProductID());
			productBO.deleteProduct(product);
			responseObject.setProduct(product);
			responseObject.setSuccessMessage("Deleted product with ID : " + productDTO.getProductID());
		} catch (GeneralException e) {
			responseObject.setFailureMessage("Error while deleting product with ID : " + productDTO.getProductID());
			logger.error(e);
		}
		return responseObject;
	}
}
