/**
 * 
 */
package com.lxl.common.component.kafka.model;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Administrator
 *
 */
public class KafkaMessage {

	private String id;

	private long timestamp;

	private String from;

	private Object data;
	
	public KafkaMessage() {
		super();
	}

	public KafkaMessage(String id, long timestamp, String from, Object data) {
		super();
		this.id = id;
		this.timestamp = timestamp;
		this.from = from;
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
