package com.xoriant.consumer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xoriant.consumer.constant.KafkaConstants;
import com.xoriant.consumer.model.Category;
import com.xoriant.consumer.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private KafkaTemplate<String, Category> kafkaTemplate;

	@Autowired
	private KafkaTemplate<String, List<Category>> kafkaTemplate2;

	@Autowired
	private CategoryService categoryService;

	@Value("${spring.kafka.topic}")
	private String topic;

	@Value("${spring.kafka.topic.name}")
	private String topicForLists;

	@PostMapping("/save")
	public ResponseEntity<Category> addNewCategory(@RequestBody Category category) {
		Category result = categoryService.addNewCategory(category);
		if (result != null) {
			kafkaTemplate.send(topic, result);
			System.out.println(KafkaConstants.MESSAGE_SENDER + result);
		}
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
		Category result = categoryService.updateCategory(category);
		if (result != null) {
			kafkaTemplate.send(topic, result);
			System.out.println(KafkaConstants.MESSAGE_SENDER + result);
		}
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@PostMapping("/saveAll")
	public ResponseEntity<List<Category>> addNewListsOfCategories(@Valid @RequestBody List<Category> categoryLists) {
		List<Category> result = categoryService.addNewListsOfCategories(categoryLists);
		if (result != null) {
			kafkaTemplate2.send(topicForLists, result);
			System.out.println(KafkaConstants.MESSAGE_SENDER + result);
		}
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
}
