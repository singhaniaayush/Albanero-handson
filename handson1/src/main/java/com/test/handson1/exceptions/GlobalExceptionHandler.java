package com.test.handson1.exceptions;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.google.gson.JsonIOException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exceptionHadnler(Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ JsonIOException.class, IOException.class })
	public ResponseEntity<String> exceptionHadnler() {
		return new ResponseEntity<>("Error while Wrting to JSON", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
