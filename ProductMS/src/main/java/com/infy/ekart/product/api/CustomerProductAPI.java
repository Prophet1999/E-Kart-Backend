package com.infy.ekart.product.api;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.ekart.product.dto.ProductDTO;
import com.infy.ekart.product.exception.EKartProductException;
import com.infy.ekart.product.service.CustomerProductService;


//Add the missing annotations
@RestController
@RequestMapping(value = "/product-api")
public class CustomerProductAPI {

	@Autowired
	private CustomerProductService customerProductService;

	@Autowired
    private Environment  environment;

    Log logger = LogFactory.getLog(CustomerProductAPI.class);

    // Get all the product details by calling getAllProducts() of
 	// CustomerProductService and return the same
	@GetMapping(value = "/products")
	public ResponseEntity<List<ProductDTO>> getAllProducts() throws EKartProductException {
		return ResponseEntity.ok(customerProductService.getAllProducts());
	}
	
	// Get the specific product by calling getProductById() of
	// CustomerProductService and return the same
	@GetMapping(value = "/product/{productId}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable("productId") Integer productId) throws EKartProductException {
		return ResponseEntity.ok(customerProductService.getProductById(productId));
	}
	
	// reduce the available quantity of product by calling reduceAvailableQuantity()
	// of CustomerProductService and return the message with property as
	// ProductAPI.REDUCE_QUANTITY_SUCCESSFULL
	@PutMapping(value = "/update/{productId}")
	public ResponseEntity<String> reduceAvailableQuantity(@PathVariable("productId") Integer productId , @RequestBody Integer quantity) throws EKartProductException {
		customerProductService.reduceAvailableQuantity(productId, quantity);
		return ResponseEntity.ok(environment.getProperty("ProductAPI.REDUCE_QUANTITY_SUCCESSFULL", String.class));
	}
}
