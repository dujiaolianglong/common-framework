/**
 * 
 */
package com.lxl.common.component.base.snowflake;

import java.util.Random;

import com.lxl.common.component.base.exception.ServiceException;

/**
 * @author Administrator
 *
 */
public class SnowflakeIdGenerator {
	
	/**
	 * 业务线标识id所占的位数
	 **/
	private final long serviceIdBits = 7L;
	/**
	 * 业务线标识支持的最大数据标识id(这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
	 */
	private final long maxServiceId = -1L ^ (-1L << serviceIdBits);
	private final long serviceId;

	/**
	 * 机器id所占的位数
	 **/
	private final long workerIdBits = 5L;
	/**
	 * 支持的最大机器id
	 */
	private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
	private final long workerId;

	/**
	 * 序列在id中占的位数
	 **/
	private final long sequenceBits = 13L;
	private final long sequenceMask = -1L ^ (-1L << sequenceBits);

	/**
	 * 开始时间戳（2018年1月1日）
	 **/
	private final long twepoch = 1514736000000L;
	/**
	 * 最后一次的时间戳
	 **/
	private volatile long lastTimestamp = -1L;
	/**
	 * 毫秒内序列
	 **/
	private volatile long sequence = 0L;
	/**
	 * 随机生成器
	 **/
	private static volatile Random random = new Random();

	/**
	 * 机器id左移位数
	 **/
	private final long workerIdShift = sequenceBits;
	/**
	 * 业务线id左移位数
	 **/
	private final long serviceIdShift = sequenceBits + workerIdBits;
	/**
	 * 时间戳左移位数
	 **/
	private final long timestampLeftShift = sequenceBits + workerIdBits + serviceIdBits;

	public SnowflakeIdGenerator(long workerId, long serviceId) {
		if ((serviceId > maxServiceId) || (serviceId < 0)) {
			throw new IllegalArgumentException(String.format("service Id can't be greater than %d or less than 0", maxServiceId));
		}
		if ((workerId > maxWorkerId) || (workerId < 0)) {
			throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
		}
		this.serviceId = serviceId;
		this.workerId = workerId;
	}

	public synchronized long nextId() {
		long timestamp = System.currentTimeMillis();
		if (timestamp < lastTimestamp) {
			throw new ServiceException("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds.");
		}
		// 如果是同一时间生成的，则进行毫秒内序列
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			// 跨毫秒时，序列号总是归0，会导致序列号为0的ID比较多，导致生成的ID取模后不均匀，所以采用10以内的随机数
			sequence = random.nextInt(10) & sequenceMask;
		}
		// 上次生成ID的时间截（设置最后时间戳）
		lastTimestamp = timestamp;

		// 移位并通过或运算拼到一起组成64位的ID
		return ((timestamp - twepoch) << timestampLeftShift) // 时间戳
				| (serviceId << serviceIdShift) // 业务线
				| (workerId << workerIdShift) // 机器
				| sequence; // 序号
	}

	/**
	 * 等待下一个毫秒的到来, 保证返回的毫秒数在参数lastTimestamp之后 不停获得时间，直到大于最后时间
	 */
	private long tilNextMillis(final long lastTimestamp) {
		long timestamp = System.currentTimeMillis();
		while (timestamp <= lastTimestamp) {
			timestamp = System.currentTimeMillis();
		}
		return timestamp;
	}

	/**
	 * 获取序号
	 *
	 * @param id
	 * @return
	 */
	public static Long getSequence(Long id) {
		String str = Long.toBinaryString(id);
		int size = str.length();
		String sequenceBinary = str.substring(size - 7, size);
		return Long.parseLong(sequenceBinary, 2);
	}

	/**
	 * 获取机器 -Ddubbo.token="666"
	 * 
	 * @param id
	 * @return
	 */
	public static Long getWorker(Long id) {
		String str = Long.toBinaryString(id);
		int size = str.length();
		String sequenceBinary = str.substring(size - 7 - 10, size - 7);
		return Long.parseLong(sequenceBinary, 2);
	}

	/**
	 * 获取业务线
	 *
	 * @param id
	 * @return
	 */
	public static Long getService(Long id) {
		String str = Long.toBinaryString(id);
		int size = str.length();
		String sequenceBinary = str.substring(size - 7 - 10 - 8, size - 7 - 10);
		return Long.parseLong(sequenceBinary, 2);
	}

	public static void main(String[] args) {
		SnowflakeIdGenerator idWorker = new SnowflakeIdGenerator(31L, 64);
		for (int i = 0; i < 1000; i++) {
			long id;
			try {
				id = idWorker.nextId();
				System.out.println(id);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
	}
}
