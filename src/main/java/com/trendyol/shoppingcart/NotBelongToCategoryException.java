package com.trendyol.shoppingcart;

import static java.lang.String.format;

public class NotBelongToCategoryException extends Throwable {

    private static final String MESSAGE_TEMPLATE = "Category{%s} of Product{%s} is not belong to Category{%s}";

    private final Product product;

    private final Category category;

    public NotBelongToCategoryException(Product product, Category category) {
        this.product = product;
        this.category = category;
    }

    @Override
    public String getMessage() {
        return format(MESSAGE_TEMPLATE, product.getCategory().getTitle(), product.getName(), category.getTitle());
    }
}
