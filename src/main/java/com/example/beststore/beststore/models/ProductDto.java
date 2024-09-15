package com.example.beststore.beststore.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class ProductDto {
    @NotEmpty(message = "Tên không được bỏ trống")
    private String name;
    @NotEmpty(message = "nhãn không được bỏ trống")
    private String brand;
    @NotEmpty(message = "loại không được bỏ trống")
    private String category;
    @Min(0)
    private double price;
    @Size(min= 10,message = "Description nên có trên 10 kí tự")
    @Size(max= 2000,message = "Description giới hạn 2000 kí tự")
    private String description;
    private MultipartFile  imageFilename;

    public @NotEmpty(message = "Tên không được bỏ trống") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "Tên không được bỏ trống") String name) {
        this.name = name;
    }

    public @NotEmpty(message = "nhãn không được bỏ trống") String getBrand() {
        return brand;
    }

    public void setBrand(@NotEmpty(message = "nhãn không được bỏ trống") String brand) {
        this.brand = brand;
    }

    public @NotEmpty(message = "loại không được bỏ trống") String getCategory() {
        return category;
    }

    public void setCategory(@NotEmpty(message = "loại không được bỏ trống") String category) {
        this.category = category;
    }

    @Min(0)
    public double getPrice() {
        return price;
    }

    public void setPrice(@Min(0) double price) {
        this.price = price;
    }

    public @Size(min = 10, message = "Description nên có trên 10 kí tự") @Size(max = 2000, message = "Description giới hạn 2000 kí tự") String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 10, message = "Description nên có trên 10 kí tự") @Size(max = 2000, message = "Description giới hạn 2000 kí tự") String description) {
        this.description = description;
    }

    public MultipartFile  getImageFilename() {
        return imageFilename;
    }

    public void setImageFilename(MultipartFile  imageFilename) {
        this.imageFilename = imageFilename;
    }
}
