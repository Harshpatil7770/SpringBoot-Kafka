package com.xoriant.consumer.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xoriant.consumer.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {

}
