package repository;

import domain.Product;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(int id);
    void update(Product product);
}
