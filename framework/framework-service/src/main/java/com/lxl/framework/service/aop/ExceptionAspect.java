package com.lxl.framework.service.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.lxl.common.component.base.entity.Response;
import com.lxl.common.component.base.enums.CommonExceptionEnum;
import com.lxl.common.component.base.exception.ServiceException;

/**
 * Title: 全局异常处理切面 Description: 利用 @ControllerAdvice + @ExceptionHandler
 * 组合处理Controller层RuntimeException异常
 * 
 */
@ControllerAdvice
@ResponseBody
public class ExceptionAspect {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionAspect.class);

	/**
	 * 400 - Bad Request
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Response<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		logger.error("could_not_read_json...", e);
		return Response.error(CommonExceptionEnum.COULD_NOT_READ_JSON.getMsg());
	}

	/**
	 * 405 - Method Not Allowed。HttpRequestMethodNotSupportedException
	 * 是ServletException的子类,需要Servlet API支持
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Response<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		logger.error("request_method_not_supported...", e);
		return Response.error(CommonExceptionEnum.REQMETHOD_NOT_SUPPORTED.getMsg());
	}

	/**
	 * 415 - Unsupported Media Type。HttpMediaTypeNotSupportedException
	 * 是ServletException的子类,需要Servlet API支持
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
	public Response<String> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
		logger.error("content_type_not_supported...", e);
		return Response.error(CommonExceptionEnum.CONTENT_TYPE_NOT_SUPPORTED.getMsg());
	}

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Response<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();
		String errorMesssage = "Invalid Request:";

		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			errorMesssage += fieldError.getDefaultMessage() + ", ";
		}
		logger.error("errorMesssage={}", errorMesssage);
		logger.error("bindingResult.getFieldError().getDefaultMessage()={}", bindingResult.getFieldError().getDefaultMessage());
		return Response.error(CommonExceptionEnum.PARAM_VALIDATE_EXCEPTION.getMsg());
	}

	/**
	 * ServiceException Error
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(ServiceException.class)
	public Response<String> handleException(ServiceException e) {
		return Response.error(e.getCode(), e.getMsg());
	}

	/**
	 * 500 - Internal Server Error
	 */
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(Exception.class)
	public Response<String> handleException(Exception e) {
		logger.error("sys error={}", e);
		return Response.error();
	}
}
