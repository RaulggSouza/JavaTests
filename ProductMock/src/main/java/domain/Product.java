package domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    final int id;
    String name;
    BigDecimal price;
    int quantity;

    public Product(int id) {
        this.id = id;
    }

    Product(int id, String name, BigDecimal price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && quantity == product.quantity && Objects.equals(name, product.name) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, quantity);
    }

    public Product updateProduct(String name, BigDecimal price, int quantity){
        setName(name);
        setPrice(price);
        setQuantity(quantity);
        return this;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
