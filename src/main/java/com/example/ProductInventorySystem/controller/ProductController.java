package com.example.ProductInventorySystem.controller;

import com.example.ProductInventorySystem.model.Product;
import com.example.ProductInventorySystem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/display")
    public List<Product> displayProducts(@RequestParam int page, @RequestParam int size) {
        return productService.displayProducts(page, size);
    }

    @PostMapping("/add")
    public String addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/update")
    public String updateProduct(@RequestParam int id, @RequestBody Product product) {
        return  productService.updateProduct(id, product);
    }

    @DeleteMapping("/delete")
    public String deleteProduct(@RequestParam int id) {
        return productService.deleteProduct(id);
    }

    @GetMapping("/filterProductsByCategory")
    public List<Product> filterProductsByCategory(@RequestParam String category, @RequestParam int page, @RequestParam int size) {
        return productService.filterProductsByCategory(category, page, size);
    }

    @GetMapping("/filterProductsByPriceRange")
    public List<Product> filterProductsByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice, @RequestParam int page, @RequestParam int size) {
        return productService.filterProductsByPriceRange(minPrice, maxPrice, page, size);
    }

    @GetMapping("/filterProductsByAvailability")
    public List<Product> filterProductsByAvailability(@RequestParam int page, @RequestParam int size) {
        return productService.filterProductsByAvailability(page, size);
    }

    @GetMapping("/sortProducts")
    public List<Product> sortProducts(@RequestParam String sortBy, @RequestParam String order, @RequestParam int page, @RequestParam int size) {
        return productService.sortProducts(sortBy, order, page, size);
    }

    @GetMapping("/checkStockLevels")
    public String checkStockLevels(@RequestParam int stockThreshold) {
        return productService.checkStockLevels(stockThreshold);
    }
}
