package com.trendyol.shoppingcart;

import java.util.Objects;

public class Product {

    private final String name;

    private final Price price;

    private Category category;

    public Product(String name, Price price, Category category) {

        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public boolean isBelongTo(Category category) {
        return this.category.equals(category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Product{" + "name='" + name + '\'' + ", price=" + price + ", category=" + category + '}';
    }
}
