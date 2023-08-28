package com.infy.ekart.product.service;

import java.lang.System.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.infy.ekart.product.dto.ProductDTO;
import com.infy.ekart.product.entity.Product;
import com.infy.ekart.product.exception.EKartProductException;
import com.infy.ekart.product.repository.ProductRepository;


//Add the missing annotations
@Service
public class CustomerProductServiceImpl implements CustomerProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
//	@Value("ProductService.PRODUCT_NOT_AVAILABLE")
//	private String PRODUCT_NOT_FOUND;
	
	@Autowired
	Environment environment;

	//This method is used to retrieve the list of all the products from database
	//Invoke appropriate method of ProductRepository, to fetch product details
	//which in turn returns a list.
	//for each product found, create and populate the ProductDTO object and add 
	//it to a List<ProductDTO>.
	//Return the above obtained list
	@Override
	public List<ProductDTO> getAllProducts() throws EKartProductException {
//		List<Product> products = productRepository.findAll();
		List<ProductDTO> productDTOs = new ArrayList<>();
//		for(Product product : products) {
//			productDTOs.add(product.getProductDTO());
//		}
//		return productDTOs;
		return productRepository.findAll().stream().map(Product::getProductDTO).toList();
	}

	//This method is used to fetch Product details of the product with the given productId
	//Invoke appropriate method of ProductRepository which will retrieve the product
	//details using the given productId (available in ProductDTO). 
	//If product exists for the given productId return the product details
	//Else, If productId does not exist, then throw an object of EKartProductException with 
	//message “ProductService.PRODUCT_NOT_AVAILABLE”
	@Override
	public ProductDTO getProductById(Integer productId) throws EKartProductException {
		Product product = productRepository.findById(productId).orElseThrow(() -> {
			throw new EKartProductException(environment.getProperty("ProductService.PRODUCT_NOT_AVAILABLE"));
		});
		return product.getProductDTO();
	}
	
	// This method is used to reduce the available quantity of product 
	// Invoke appropriate methods of ProductRepository to retrieve the product 
	// details using the given productId 
	// If product does not exist, then throw an object of EkartProductException 
	// with message “ProductService.PRODUCT_NOT_AVAILABLE”
	// Else, reduce the quantity of the retrieved product with the given number of quantity
	@Override
	public void reduceAvailableQuantity(Integer productId, Integer quantity) throws EKartProductException {
//		Product product = productRepository.findById(productId).orElseThrow(() -> {
//			throw new EKartProductException(environment.getProperty("ProductService.PRODUCT_NOT_AVAILABLE"));
//		});
		Optional<Product> proOptional = productRepository.findById(productId);
		if(proOptional.isEmpty()) {
			throw new EKartProductException(environment.getProperty("ProductService.PRODUCT_NOT_AVAILABLE"));			
		}
		Product product = proOptional.get();
		if (product.getAvailableQuantity() - quantity > 0 ) {
			product.setAvailableQuantity(product.getAvailableQuantity() - quantity);
			productRepository.save(product);
		}
	}
}
