package com.srit.market.home.ui.home.category;

import java.util.List;

public class CatModel {
    private List<CategoryModel> Category;

    public List<CategoryModel> getCategory() {
        return Category;
    }

    @Override
    public String toString() {
        return "CatModel{" +
                "Category=" + Category +
                '}';
    }
}
