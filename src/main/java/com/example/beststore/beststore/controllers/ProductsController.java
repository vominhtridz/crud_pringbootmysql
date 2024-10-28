package com.example.beststore.beststore.controllers;

import com.example.beststore.beststore.models.Product;
import com.example.beststore.beststore.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    private final ProductService productService;

    public ProductsController(ProductService productService) {
        this.productService = productService;
    }
    @ResponseBody
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return new ResponseEntity<Product>(product,HttpStatus.OK);
    }
    @ResponseBody
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);

        return new ResponseEntity<Product>(createdProduct,HttpStatus.OK);
    }
    @ResponseBody
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product existingProduct = productService.getProductById(id);

        if (existingProduct == null) {
            return ResponseEntity.notFound().build();
        }

        Product updatedProduct = productService.updateProduct(id, product);
        return new ResponseEntity<Product>(updatedProduct,HttpStatus.OK);
    }
    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<String> Product(@PathVariable Long id) {
        if (productService.getProductById(id) == null) {
             return new ResponseEntity<String>("Không thầy người dùng.",HttpStatus.NOT_FOUND);
        }

        productService.deleteProduct(id);
        return new ResponseEntity<String>("Xóa thành công.",HttpStatus.OK);
    }
}
