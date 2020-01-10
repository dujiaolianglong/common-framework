package com.lxl.common.component.base.enums;

/**
 * 异常编码
 * 
 * @author Administrator
 *
 */
public enum CommonExceptionEnum {
	
	SUCCESS("00000", "操作成功"), 
	SYS_ERROR("00001", "系统异常"),
	
	REQMETHOD_NOT_SUPPORTED("200002", "request_method_not_supported"),
	CONTENT_TYPE_NOT_SUPPORTED("200003", "content_type_not_supported"),
	PARAM_VALIDATE_EXCEPTION("200004", "parameter_validation_exception"),
	COULD_NOT_READ_JSON("200005", "could_not_read_json");
	
	private String code;
	private String msg;

	private CommonExceptionEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
}
