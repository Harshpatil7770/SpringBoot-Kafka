package com.xoriant.consumer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	private KafkaTemplate<String, String> kafkaTemplate3;

	@Autowired
	private CategoryService categoryService;

	@Value("${spring.kafka.topic}")
	private String topic;

	@Value("${spring.kafka.topic.name}")
	private String topicForLists;

	@Value("${spring.kafka.first}")
	private String userTopic;

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

	@PutMapping("/updateAll")
	public ResponseEntity<List<Category>> updateListsOfCategories(@Valid @RequestBody List<Category> catLists) {
		List<Category> result = categoryService.updateListsOfCategories(catLists);
		if (result != null) {
			kafkaTemplate2.send(topicForLists, result);
			System.out.println(KafkaConstants.MESSAGE_SENDER + result);
		}
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@GetMapping("/{categoryName}")
	public ResponseEntity<Category> findByCategoryName(@PathVariable String categoryName) {
		Category result = categoryService.findByCategoryName(categoryName).orElse(null);
		if (result != null) {
			kafkaTemplate.send(topic, result);
			System.out.println(KafkaConstants.MESSAGE_SENDER + result);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/find")
	public ResponseEntity<Category> findById(@RequestParam long categoryId) {
		Category result = categoryService.findById(categoryId).orElse(null);
		if (result != null) {
			kafkaTemplate.send(topic, result);
			System.out.println(KafkaConstants.MESSAGE_SENDER + result);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Category>> findAll() {
		List<Category> result = categoryService.findAll();
		if (result != null) {
			kafkaTemplate2.send(topicForLists, result);
			System.out.println(KafkaConstants.MESSAGE_SENDER + result);
		}
		return new ResponseEntity<List<Category>>(result, HttpStatus.OK);

	}

	@GetMapping("/findAll/alphabeticalOrder")
	public ResponseEntity<List<Category>> findAllInAlphabeticalOrder() {
		List<Category> result = categoryService.findAllInAlphabeticalOrder();
		if (result != null) {
			kafkaTemplate2.send(topicForLists, result);
			System.out.println(KafkaConstants.MESSAGE_SENDER + result);
		}
		return new ResponseEntity<List<Category>>(result, HttpStatus.OK);
	}

	@GetMapping("/find/{firstLetter}")
	public ResponseEntity<List<Category>> findByFirstCharacterOfCategoryName(@PathVariable String firstLetter) {
		List<Category> result = categoryService.findByFirstCharacterOfCategoryName(firstLetter.toUpperCase());
		if (result != null) {
			kafkaTemplate2.send(topicForLists, result);
			System.out.println(KafkaConstants.MESSAGE_SENDER + result);
		}
		return new ResponseEntity<List<Category>>(result, HttpStatus.OK);
	}

	@GetMapping("/updateOrsaveAll")
	public ResponseEntity<List<Category>> updateAllCategoryNameAndSaveInUpperCaseLetter() {
		List<Category> result = categoryService.updateAllCategoryNameAndSaveInUpperCaseLetter();
		if (result != null) {
			kafkaTemplate2.send(topicForLists, result);
			System.out.println(KafkaConstants.MESSAGE_SENDER + result);
		}
		return new ResponseEntity<List<Category>>(result, HttpStatus.OK);
	}

	@GetMapping("/find/{fromCategoryId}/{toCategoryId}")
	public ResponseEntity<List<Category>> findInBetweenCategoryId(@PathVariable long fromCategoryId,
			@PathVariable long toCategoryId) {
		List<Category> result = categoryService.findInBetweenCategoryId(fromCategoryId, toCategoryId);
		if (result != null) {
			kafkaTemplate2.send(topicForLists, result);
			System.out.println(KafkaConstants.MESSAGE_SENDER + result);
		}
		return new ResponseEntity<List<Category>>(result, HttpStatus.OK);
	}

	@GetMapping("/findAll-below/{belowCategoryId}")
	public ResponseEntity<List<Category>> findByCategoryIdLessThan(@PathVariable long belowCategoryId) {
		List<Category> result = categoryService.findByBelowtheCategoryId(belowCategoryId);
		if (result != null) {
			kafkaTemplate2.send(topicForLists, result);
			System.out.println(KafkaConstants.MESSAGE_SENDER + result);
		}
		return new ResponseEntity<List<Category>>(result, HttpStatus.OK);
	}

	@GetMapping("/findAll-above/{aboveCategoryId}")
	public ResponseEntity<List<Category>> findAbovetheCategoryId(@PathVariable long aboveCategoryId) {
		List<Category> result = categoryService.findAbovetheCategoryId(aboveCategoryId);
		if (result != null) {
			kafkaTemplate2.send(topicForLists, result);
			System.out.println(KafkaConstants.MESSAGE_SENDER + result);
		}
		return new ResponseEntity<List<Category>>(result, HttpStatus.OK);
	}

	@DeleteMapping("/{categoryId}")
	void deleteCategory(@PathVariable long categoryId) {
		categoryService.deleteCategory(categoryId);
		kafkaTemplate3.send(userTopic, "Deleted Existing Category Succesfully: " + categoryId);
	}
}
