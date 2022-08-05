package com.xoriant.consumer.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xoriant.consumer.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {

	Optional<Category> findByCategoryName(String categoryName);

	Optional<Category> findByCategoryNameStartsWith(String firstLetter);

	List<Category> findByCategoryIdIsBetween(long fromCategoryId, long toCategoryId);

}
