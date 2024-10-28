package com.example.beststore.beststore.repositories;
import com.example.beststore.beststore.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ProductsRepository extends JpaRepository<Product, Long> {


}


