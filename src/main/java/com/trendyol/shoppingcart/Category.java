package com.trendyol.shoppingcart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.nonNull;

public class Category {

    private final String title;

    private Category parent;

    public Category(String title) {
        this.title = title;
    }

    public Category(String title, Category parent) {
        this.title = title;
        this.parent = parent;
    }

    public String getTitle() {
        return title;
    }

    public boolean isChildOf(Category target) {
        Category category = this;
        while(nonNull(category)){
            if (category.equals(target)) {
                return true;
            }
            category = category.parent;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Category category = (Category) o;
        return Objects.equals(title, category.title);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "Category{" + "title='" + title + '\''  + '}';
    }

    public List<Category> getCategoriesBottomToTopOrdered() {

        List<Category> parents = new ArrayList<>();
        Category category = this;
        while(nonNull(category)){
            parents.add(category);
            category = category.parent;
        }
        return parents;
    }
}
