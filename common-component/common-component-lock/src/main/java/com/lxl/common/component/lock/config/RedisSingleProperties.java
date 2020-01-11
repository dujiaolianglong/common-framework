/**
 * 
 */
package com.lxl.common.component.lock.config;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Administrator
 *
 */
public class RedisSingleProperties {
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
