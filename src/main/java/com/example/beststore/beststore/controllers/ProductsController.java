package com.example.beststore.beststore.controllers;
import com.example.beststore.beststore.models.Product;
import com.example.beststore.beststore.models.ProductDto;
import com.example.beststore.beststore.services.ProductsRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/")
public class ProductsController {
    @Autowired
    private ProductsRepository repo;


    @GetMapping({"", "/products"})
    public String ShowProductList(Model model) {
        List<Product> products = repo.findAll();//Sort.by(Sort.Direction.DESC, "id")
        model.addAttribute("products", products);
        return "products/index";
    }
//    Insert Product
    @GetMapping({"","/products/create"})
    public String ShowCreatePage(Model model){
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "products/create";
    }
    // Create Product
    @PostMapping({"","/create"})
    public String CreateProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult result) throws IOException {
        MultipartFile ImageFileName = productDto.getImageFilename();
        if(ImageFileName.isEmpty()) {
            result.addError(new FieldError("productDto", "imageFilename", "ảnh chưa được tải lên"));
        }
        if(result.hasErrors()){
            return "products/create";
        }
        //save file
        Date date = new Date();
        String StorageFileName = date.getTime() + "_" + ImageFileName.getOriginalFilename();
        try{
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = ImageFileName.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + StorageFileName), StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch(Exception ex){
            System.out.print("Exception" + ex.getMessage());
        }
        Product product = new Product();
        product.setName(productDto.getName());
        product.setCreatedAt(date);
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setImageFilename(StorageFileName);
        repo.save(product);
        return "redirect:/products";
    }
    //Get Edit Product
    @GetMapping({"","/products/edit"})
    public String GetEditProduct(Model model, @RequestParam int id){
        try {
            Product product = repo.findById(id).get();
            model.addAttribute("product", product);
            ProductDto productDto = new ProductDto();
            productDto.setDescription(product.getDescription());
            productDto.setBrand(product.getBrand());
            productDto.setCategory(product.getCategory());
            productDto.setName(product.getName());
            productDto.setPrice(product.getPrice());
            model.addAttribute("productDto", productDto);
        }
        catch(Exception ex){
            System.out.print("Exception" + ex.getMessage());
        }
        return "products/editProduct";
    }
// Edit Product
    @PostMapping({"","/edit"})
    public String EditProduct(Model model, @RequestParam int id, @Valid @ModelAttribute ProductDto productDto, BindingResult result)  {
        MultipartFile ImageFileName = productDto.getImageFilename();
        Product product = repo.findById(id).get();
        Date date = new Date();
        String StorageFileName = date.getTime() + "_" + ImageFileName.getOriginalFilename();
        if(result.hasErrors()){
            return "products/edit(id=${id})";
        }

        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = ImageFileName.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + StorageFileName), StandardCopyOption.REPLACE_EXISTING);
            }

        }
        catch(Exception ex){
            System.out.print("Exception" + ex.getMessage());
        }
        product.setName(productDto.getName());
        product.setCreatedAt(date);
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setImageFilename(StorageFileName);
        repo.save(product);
        return "redirect:/products";
    }
    // Delete Product
    @GetMapping({"","/products/delete"})
    public String DeleteProduct(Model model, @RequestParam int id){
        Product product = repo.findById(id).get();
        Path imagePath = Paths.get("public/images/"+ product.getImageFilename());

        try {
            Files.delete(imagePath);
        }
        catch(Exception ex){
            System.out.print("Exception" + ex.getMessage());
        }
        repo.delete(product);
        return "redirect:/products";
    }
}
