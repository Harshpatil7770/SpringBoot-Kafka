package com.xoriant.consumer.service;

import java.util.List;

import com.xoriant.consumer.model.Category;

public interface CategoryService {

	Category addNewCategory(Category category);

	Category updateCategory(Category category);

	List<Category> addNewListsOfCategories(List<Category> categoryLists);
}
