package com.infy.ekart.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.infy.ekart.product.entity.Product;

@Repository
//extends it with required Interface
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
