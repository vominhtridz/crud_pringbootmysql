package com.example.beststore.beststore.services;
import com.example.beststore.beststore.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductsRepository extends JpaRepository<Product, Integer> {


}


