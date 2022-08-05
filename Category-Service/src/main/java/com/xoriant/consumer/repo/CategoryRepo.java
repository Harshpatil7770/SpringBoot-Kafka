package com.xoriant.consumer.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xoriant.consumer.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {

	List<Category> findByCategoryIdIsBetween(long fromCategoryId, long toCategoryId);

	Optional<Category> findByCategoryName(String upperCase);

	List<Category> findByCategoryIdLessThan(long belowCategoryId);

	List<Category> findByCategoryIdGreaterThan(long aboveCategoryId);

}
