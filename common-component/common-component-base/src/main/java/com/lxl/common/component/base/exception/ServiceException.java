package com.lxl.common.component.base.exception;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author Administrator
 *
 */
public class ServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1211295645259362850L;

	private String code;

	private String msg;

	public ServiceException() {
		super();
	}

	public ServiceException(String msg) {
		super();
		this.msg = msg;
	}

	public ServiceException(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
