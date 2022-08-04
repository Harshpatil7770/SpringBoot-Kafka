package com.xoriant.consumer.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xoriant.consumer.dao.CategoryRepo;
import com.xoriant.consumer.exception.ElementNotFoundException;
import com.xoriant.consumer.model.Category;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public Category addNewCategory(@Valid Category category) {

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
		updateCategory.setCategoryName(category.getCategoryName());
		categoryRepo.save(category);
		return category;
	}

	@Override
	public List<Category> addNewListsOfCategories(@Valid List<Category> categoryLists) {

		return categoryRepo.saveAll(categoryLists);
	}

}
