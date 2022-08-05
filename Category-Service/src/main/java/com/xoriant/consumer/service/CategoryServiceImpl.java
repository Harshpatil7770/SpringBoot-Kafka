package com.xoriant.consumer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xoriant.consumer.exception.ElementNotFoundException;
import com.xoriant.consumer.model.Category;
import com.xoriant.consumer.repo.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public Category addNewCategory(@Valid Category category) {
		category.getCategoryName().toUpperCase();
		return categoryRepo.save(category);
	}

	@Override
	public Category updateCategory(@Valid Category category) {
		Optional<Category> existingCategory = categoryRepo.findById(category.getCategoryId());
		if (!existingCategory.isPresent()) {
			throw new ElementNotFoundException();
		}
		Category updateCategory = categoryRepo.findById(category.getCategoryId()).orElse(null);
		updateCategory.setCategoryId(category.getCategoryId());
		updateCategory.setCategoryName(category.getCategoryName().toUpperCase());
		categoryRepo.save(category);
		return category;
	}

	@Override
	public List<Category> addNewListsOfCategories(@Valid List<Category> categoryLists) {
		for (Category category : categoryLists) {
			category.getCategoryName().toUpperCase();
			categoryLists.add(category);
		}

		return categoryRepo.saveAll(categoryLists);
	}

	@Override
	public List<Category> updateListsOfCategories(@Valid List<Category> catLists) {

		List<Category> updatedListsofCategories = new ArrayList<>();
		for (Category category : catLists) {
			Optional<Category> eachCategory = categoryRepo.findById(category.getCategoryId());
			if (!eachCategory.isPresent()) {
				throw new ElementNotFoundException();
			}
		}
		for (Category category : catLists) {
			Category updateCategory = categoryRepo.findById(category.getCategoryId()).orElse(null);
			updateCategory.setCategoryId(category.getCategoryId());
			updateCategory.setCategoryName(category.getCategoryName().toUpperCase());
			categoryRepo.save(updateCategory);
			updatedListsofCategories.add(updateCategory);
		}
		return updatedListsofCategories;
	}

	@Override
	public Optional<Category> findById(long categoryId) {
		Optional<Category> existingCategory = categoryRepo.findById(categoryId);
		if (!existingCategory.isPresent()) {
			throw new ElementNotFoundException();
		}
		return existingCategory;
	}

	@Override
	public Optional<Category> findByCategoryName(String categoryName) {
		Optional<Category> existingCategory = categoryRepo.findByCategoryName(categoryName.toUpperCase());
		if (!existingCategory.isPresent()) {
			throw new ElementNotFoundException();
		}
		return existingCategory;
	}

	@Override
	public List<Category> findAll() {
		List<Category> existingCategoryLists = categoryRepo.findAll();
		if (existingCategoryLists.isEmpty()) {
			throw new ElementNotFoundException();
		}
		return existingCategoryLists;
	}

	@Override
	public List<Category> findAllInAlphabeticalOrder() {
		List<Category> existingCategoryLists = categoryRepo.findAll();
		if (existingCategoryLists.isEmpty()) {
			throw new ElementNotFoundException();
		}
		List<Category> alphabeticalOrderCatLists = existingCategoryLists.stream()
				.sorted((e1, e2) -> e1.getCategoryName().compareTo(e2.getCategoryName())).collect(Collectors.toList());
		return alphabeticalOrderCatLists;
	}

	@Override
	public List<Category> findByFirstCharacterOfCategoryName(String firstLetter) {
		List<Category> categoryLists = categoryRepo.findAll();
		if (categoryLists.isEmpty()) {
			throw new ElementNotFoundException();
		}
		List<Category> firstLetterSameCategoryNameLists = categoryLists.stream()
				.filter((e) -> e.getCategoryName().startsWith(firstLetter.toUpperCase())).collect(Collectors.toList());
		return firstLetterSameCategoryNameLists;
	}

	@Override
	public List<Category> findInBetweenCategoryId(long fromCategoryId, long toCategoryId) {
		List<Category> existingCategory = categoryRepo.findByCategoryIdIsBetween(fromCategoryId, toCategoryId);
		if (existingCategory.isEmpty()) {
			throw new ElementNotFoundException();
		}
		return existingCategory;
	}

	@Override
	public List<Category> findByBelowtheCategoryId(long belowCategoryId) {
		List<Category> existingCategory = categoryRepo.findByCategoryIdLessThan(belowCategoryId);
		if (existingCategory.isEmpty()) {
			throw new ElementNotFoundException();
		}
		return existingCategory;
	}

	@Override
	public List<Category> findAbovetheCategoryId(long aboveCategoryId) {
		List<Category> existingCategory = categoryRepo.findByCategoryIdGreaterThan(aboveCategoryId);
		if (existingCategory.isEmpty()) {
			throw new ElementNotFoundException();
		}
		return existingCategory;
	}

	@Override
	public void deleteCategory(long categoryId) {

		Optional<Category> existingCategory = categoryRepo.findById(categoryId);
		if (!existingCategory.isPresent()) {
			throw new ElementNotFoundException();
		}
		categoryRepo.deleteById(categoryId);
	}

	@Override
	public List<Category> updateAllCategoryNameAndSaveInUpperCaseLetter() {
		List<Category> categoryLists = categoryRepo.findAll();
		List<Category> updateListsOfCategory = new ArrayList<>();
		if (categoryLists.isEmpty()) {
			throw new ElementNotFoundException();
		}

		for (Category eachCategory : categoryLists) {
			Category updatedCategory = new Category();
			updatedCategory.setCategoryId(eachCategory.getCategoryId());
			updatedCategory.setCategoryName(eachCategory.getCategoryName().toUpperCase());
			categoryRepo.save(updatedCategory);
			updateListsOfCategory.add(updatedCategory);
		}

		return updateListsOfCategory;
	}

}
