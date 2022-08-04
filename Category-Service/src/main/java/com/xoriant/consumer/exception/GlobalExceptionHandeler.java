package com.xoriant.consumer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.xoriant.consumer.constant.ExceptionConstants;

@ControllerAdvice
public class GlobalExceptionHandeler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ElementNotFoundException.class)
	public ResponseEntity<String> elementNotFoundException(ElementNotFoundException elementNotFoundException) {
		return new ResponseEntity<>(ExceptionConstants.ELEMENT_NOT_FOUND, HttpStatus.BAD_REQUEST);
	}
}
