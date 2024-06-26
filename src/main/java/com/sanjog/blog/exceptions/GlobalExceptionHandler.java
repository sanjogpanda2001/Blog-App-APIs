package com.sanjog.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sanjog.blog.payloads.ApiResponse;

@RestControllerAdvice
	public class GlobalExceptionHandler {
		@ExceptionHandler(ResourceNotFoundException.class)
		public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException e){
			String message=e.getMessage();
			ApiResponse apires=new ApiResponse(message,false);
			return new ResponseEntity<ApiResponse>(apires,HttpStatus.NOT_FOUND);
		}
		@ExceptionHandler(MethodArgumentNotValidException.class)
		public ResponseEntity<Map<String,String>> handlerNotValidException(MethodArgumentNotValidException e){
			Map<String,String> mp=new HashMap<>();
			e.getBindingResult().getAllErrors().forEach((error)->{
				String fieldname=((FieldError)error).getField();
				String message=error.getDefaultMessage();
				mp.put(fieldname, message);
			});
			return new ResponseEntity<>(mp,HttpStatus.BAD_REQUEST);
		}
	}



