/**
 * 
 */
package com.lxl.common.component.lock;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Administrator
 *
 */
public class RedisSentinelProperties {
	/**
	 * 哨兵master 名称
	 */
	private String master;

	/**
	 * 哨兵节点
	 */
	private String nodes;

	/**
	 * 哨兵配置
	 */
	private boolean masterOnlyWrite;

	/**
	 *
	 */
	private int failMax;

	public String getMaster() {
		return master;
	}

	public void setMaster(String master) {
		this.master = master;
	}

	public String getNodes() {
		return nodes;
	}

	public void setNodes(String nodes) {
		this.nodes = nodes;
	}

	public boolean isMasterOnlyWrite() {
		return masterOnlyWrite;
	}

	public void setMasterOnlyWrite(boolean masterOnlyWrite) {
		this.masterOnlyWrite = masterOnlyWrite;
	}

	public int getFailMax() {
		return failMax;
	}

	public void setFailMax(int failMax) {
		this.failMax = failMax;
	}

	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}

}
