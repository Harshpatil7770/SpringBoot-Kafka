package com.xoriant.consumer.service;

import java.util.List;
import java.util.Optional;

import com.xoriant.consumer.model.Category;

public interface CategoryService {

	Category addNewCategory(Category category);

	Category updateCategory(Category category);

	List<Category> addNewListsOfCategories(List<Category> categoryLists);

	List<Category> updateListsOfCategories(List<Category> catLists);

	Optional<Category> findById(long categorId);

	Optional<Category> findByCategoryName(String categoryName);

	List<Category> findAll();

	List<Category> findAllInAlphabeticalOrder();

	List<Category> findByFirstCharacterOfCategoryName(String firstLetter);

	List<Category> findInBetweenCategoryId(long fromCategoryId, long toCategoryId);

	List<Category> findByBelowtheCategoryId(long belowCategoryId);

	List<Category> findAbovetheCategoryId(long aboveCategoryId);

	void deleteCategory(long categoryId);

	List<Category> updateAllCategoryNameAndSaveInUpperCaseLetter();

}
