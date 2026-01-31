package com.shop.online_shopping_backend.controller;

import com.shop.online_shopping_backend.dto.ApiResponse;
import com.shop.online_shopping_backend.dto.ProductRequest;
import com.shop.online_shopping_backend.model.Product;
import com.shop.online_shopping_backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Tag(name = "Products", description = "Product management endpoints")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new product", description = "Create a new product (Admin only)")
    public ResponseEntity<ApiResponse<Product>> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        Product product = productService.createProduct(productRequest);
        ApiResponse<Product> response = new ApiResponse<>(true, "Product created successfully", product);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Get all products with pagination support")
    public ResponseEntity<ApiResponse<Page<Product>>> getAllProducts(Pageable pageable) {
        Page<Product> products = productService.getAllProducts(pageable);
        ApiResponse<Page<Product>> response = new ApiResponse<>(true, "Products retrieved successfully", products);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Get a specific product by its ID")
    public ResponseEntity<ApiResponse<Product>> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        ApiResponse<Product> response = new ApiResponse<>(true, "Product retrieved successfully", product);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a product", description = "Update an existing product (Admin only)")
    public ResponseEntity<ApiResponse<Product>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest productRequest) {
        Product product = productService.updateProduct(id, productRequest);
        ApiResponse<Product> response = new ApiResponse<>(true, "Product updated successfully", product);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a product", description = "Delete a product by ID (Admin only)")
    public ResponseEntity<ApiResponse<Object>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        ApiResponse<Object> response = new ApiResponse<>(true, "Product deleted successfully");
        return ResponseEntity.ok(response);
    }
}
