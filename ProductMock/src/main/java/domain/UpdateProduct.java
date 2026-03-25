package domain;

import exceptions.ProductNotFoundException;
import repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

public class UpdateProduct {
    private final ProductRepository repo;

    public UpdateProduct(ProductRepository repo) {
        this.repo = repo;
    }

    public Product updateProduct(int id, String name, BigDecimal price, int quantity){
        if (id <= 0) throw new IllegalArgumentException("id must be positive");
        Objects.requireNonNull(name, "Name must not be null");
        if (name.isBlank()) throw new IllegalArgumentException("Name must not be an empty string");
        Objects.requireNonNull(price, "Price must not be null");
        if (price.doubleValue() <= 0.0) throw new IllegalArgumentException("Price must be a positive value");
        if (quantity < 0) throw new IllegalArgumentException("Quantity must not be negative");

        Optional<Product> product = repo.findById(id);
        if (product.isEmpty()) throw new ProductNotFoundException("Product not found. Id: "+id);

        Product updatedProduct = product.get().updateProduct(name, price, quantity);
        repo.update(updatedProduct);
        return updatedProduct;
    }
}
