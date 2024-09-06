package com.example.ProductInventorySystem.service;

import com.example.ProductInventorySystem.exception.ProductNotFoundException;
import com.example.ProductInventorySystem.exception.ProductsInStockException;
import com.example.ProductInventorySystem.model.Product;
import com.example.ProductInventorySystem.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private EmailService emailService;

    private final String adminEmail = "mitalidere030@gmail.com";


    public List<Product> displayProducts(int page, int size) {
        return productRepository.displayProducts(page, size);
    }

    public String addProduct(Product product) {
        return productRepository.addProduct(new Product(product.getId(), product.getName(), product.getPrice(), product.getCategory(), product.getQuantity()));
    }

    public String updateProduct(int id, Product product) {
        return productRepository.updateProduct(id, product);
    }

    public String deleteProduct(int id) {
        return productRepository.deleteProduct(id);
    }

    public List<Product> filterProductsByCategory(String category, int page, int size) {
        return productRepository.filterProductsByCategory(category, page, size);
    }

    public List<Product> filterProductsByPriceRange(double minPrice, double maxPrice, int page, int size) {
        return productRepository.filterProductsByPriceRange(minPrice, maxPrice, page, size);
    }

    public List<Product> filterProductsByAvailability(int page, int size) {
        return productRepository.filterProductsByAvailability(page, size);
    }

    public List<Product> sortProducts(String sortBy, String order, int page, int size) {
        return productRepository.sortProducts(sortBy, order, page, size);
    }

    public String checkStockLevels(int stockThreshold) {
        List<Product> allProducts = productRepository.displayProducts(1, Integer.MAX_VALUE);
        List<Product> lowStockProducts = allProducts.stream()
                .filter(product -> product.getQuantity() < stockThreshold)
                .toList();

        if (!lowStockProducts.isEmpty()) {
            emailService.sendLowStockNotification(adminEmail, lowStockProducts, stockThreshold);
        }
        else {
            throw new ProductsInStockException("All products are in stock");
        }
        return "Email sent successfully";
    }
}
