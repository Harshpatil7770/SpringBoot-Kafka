package com.xoriant.consumer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.xoriant.consumer.exception.ElementNotFoundException;
import com.xoriant.consumer.model.Category;
import com.xoriant.consumer.repo.CategoryRepo;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

	@Mock
	private CategoryRepo categoryRepo;

	@InjectMocks
	private CategoryServiceImpl categoryServiceImpl;

	private Category category1;

	private Category category2;

	private List<Category> catLists;

	@BeforeEach
	void setUp() {
		category1 = new Category(101, "Mobile");
		category2 = new Category(102, "Lenovo");
		catLists = new ArrayList<>();
	}

	@Test
	void addNewCategory() {
		when(categoryRepo.save(category1)).thenReturn(category1);
		Category result = categoryServiceImpl.addNewCategory(category1);
		assertNotNull(result);
		assertEquals(category1, categoryServiceImpl.addNewCategory(category1));
	}

	@Test
	void updateCategory() {
		Optional<Category> existingCategory = Optional.of(category1);
		when(categoryRepo.findById(101l)).thenReturn(existingCategory);
		category1.setCategoryName("Kids Wear");
		when(categoryRepo.save(category1)).thenReturn(category1);
		assertEquals(category1, categoryServiceImpl.updateCategory(category1));
	}

	@Test
	void updateCategory_throwsException_ifCategoryIdNotFound() {
		long categoryId = 501l;
		doThrow(ElementNotFoundException.class).when(categoryRepo).findById(categoryId);
		assertThrows(ElementNotFoundException.class, () -> {
			categoryRepo.findById(categoryId);
		});
	}

	@Test
	void findById() {
		long categoryId = 101;
		Optional<Category> existingCategory = Optional.of(category1);
		when(categoryRepo.findById(categoryId)).thenReturn(existingCategory);
		assertEquals(existingCategory, categoryServiceImpl.findById(categoryId));
	}

	@Test
	void findById_throwsException_ifCategoryIdIsNotPresent() {
		long category_Id = 501;
		doThrow(ElementNotFoundException.class).when(categoryRepo).findById(category_Id);
		assertThrows(ElementNotFoundException.class, () -> {
			categoryServiceImpl.findById(category_Id);
		});
	}

	@Test
	void addNewListsOfCategories() {
		when(categoryRepo.saveAll(catLists)).thenReturn(catLists);
		assertEquals(catLists, categoryServiceImpl.addNewListsOfCategories(catLists));
	}
}
