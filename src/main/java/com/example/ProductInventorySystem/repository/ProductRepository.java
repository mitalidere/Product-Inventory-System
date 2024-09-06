package com.example.ProductInventorySystem.repository;

import com.example.ProductInventorySystem.exception.ProductNotFoundException;
import com.example.ProductInventorySystem.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.List;

@Repository
public class ProductRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper rowMapper = (ResultSet rs, int rowNum) -> {
        Product product = new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getInt(5));
        return product;
    };

    public List<Product> displayProducts(int page, int size) {
        int offset = (page - 1) * size;
        String sql = "SELECT * FROM Product LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, rowMapper, size, offset);
    }

    public String addProduct(Product product) {
        String sql = "INSERT INTO Product VALUES (?,?,?,?,?)";
        int changes = jdbcTemplate.update(sql, product.getId(), product.getName(), product.getPrice(), product.getCategory(), product.getPrice());
        return changes > 0 ? "Record added" : "Record not added";
    }

    public String updateProduct(int id, Product product) {
        String sql = "UPDATE Product SET name=?, price=?, category=?, quantity=? WHERE id=?";
        int changes = jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getCategory(), product.getQuantity(), id);
        if(changes > 0) {
            return "Record updated";
        }
        throw new ProductNotFoundException("Product not found of id "+id);
    }

    public String deleteProduct(int id) {
        String sql = "DELETE FROM Product WHERE id=?";
        int changes = jdbcTemplate.update(sql, id);
        if(changes > 0) {
            return "Record deleted";
        }
        throw new ProductNotFoundException("Product not found of id "+id);
    }

    public List<Product> filterProductsByCategory(String category, int page, int size) {
        int offset = (page - 1) * size;
        String sql = "SELECT * FROM Product WHERE category = ? LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, rowMapper, category, size, offset);
    }

    public List<Product> filterProductsByPriceRange(double minPrice, double maxPrice, int page, int size) {
        int offset = (page - 1) * size;
        String sql = "SELECT * FROM Product WHERE price BETWEEN ? AND ? LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, rowMapper, minPrice, maxPrice, size, offset);
    }

    public List<Product> filterProductsByAvailability(int page, int size) {
        int offset = (page - 1) * size;
        String sql = "SELECT * FROM Product WHERE quantity > 0 LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, rowMapper, size, offset);
    }

    public List<Product> sortProducts(String sortBy, String order, int page, int size) {
        String validSortBy = switch (sortBy.toLowerCase()) {
            case "name" -> "name";
            case "price" -> "price";
            case "quantity" -> "quantity";
            default -> "id";
        };

        String validOrder = order.equalsIgnoreCase("desc") ? "DESC" : "ASC";

        int offset = (page - 1) * size;
        String sql = "SELECT * FROM Product ORDER BY " + validSortBy + " " + validOrder + " LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, rowMapper, size, offset);
    }


}
