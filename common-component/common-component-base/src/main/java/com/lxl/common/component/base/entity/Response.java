package com.lxl.common.component.base.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lxl.common.component.base.constant.DateFormatConstant;
import com.lxl.common.component.base.enums.CommonExceptionEnum;

/**
 * 封装返回数据格式实体类
 * 
 * @author Administrator
 *
 */
public class Response<T> {

	private String code;

	private String msg;

	private T data;

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

	public Object getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static <T> Response<T> ok(T data) {
		Response<T> response = new Response<>();
		response.setCode(CommonExceptionEnum.SUCCESS.getCode());
		response.setMsg(CommonExceptionEnum.SUCCESS.getMsg());
		response.setData(data);
		return response;
	}

	public static <T> Response<T> ok() {
		Response<T> response = new Response<>();
		response.setCode(CommonExceptionEnum.SUCCESS.getCode());
		response.setMsg(CommonExceptionEnum.SUCCESS.getMsg());
		return response;
	}

	public static <T> Response<T> error() {
		Response<T> response = new Response<>();
		response.setCode(CommonExceptionEnum.SYS_ERROR.getCode());
		response.setMsg(CommonExceptionEnum.SYS_ERROR.getMsg());
		return response;
	}

	public static <T> Response<T> error(String msg) {
		Response<T> response = new Response<>();
		response.setMsg(msg);
		return response;
	}

	public static <T> Response<T> error(String code, String msg) {
		Response<T> response = new Response<>();
		response.setCode(code);
		response.setMsg(msg);
		return response;
	}

	@Override
	public String toString() {
		return JSON.toJSONStringWithDateFormat(this, DateFormatConstant.yyyy_MM_ddHHmmss, SerializerFeature.WriteNullStringAsEmpty);
	}

}
