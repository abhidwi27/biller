package com.app.biller.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@ControllerAdvice
public class ExceptionController extends Exception{
	

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="General Exception occured")
	@ExceptionHandler(Exception.class)
	public void handleGenericException(){
		logger.error("Exception handler executed");
		//returning 404 error code
	}
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="IOException occured")
	@ExceptionHandler(IOException.class)
	public void handleIOException(){
		logger.error("IOException handler executed");
		//This method is for future purpose, currently, no code in the system is explicitly throwing this error.
		//This may be useful if any error occurs in reading any of the excel files.
	}
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="IOException occured")
	@ExceptionHandler(SQLException.class)
	public void handleSQLException(){
		logger.error("IOException handler executed");
		//This method is for future purpose, currently, no code in the system is explicitly throwing this error.
		
	}
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="IOException occured")
	@ExceptionHandler(FileNotFoundException.class)
	public void handleFileNotFoundException(){
		logger.error("IOException handler executed");
		//This method is for future purpose, currently, no code in the system is explicitly throwing this error.
	}
	
	@RequestMapping(value = "/error.do", method = RequestMethod.GET)
	public String getError(HttpServletRequest request, HttpSession userSession) {		
		return "error";
	}

}
