/**
 * 
 */
package com.lxl.common.component.base.snowflake;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lxl.common.component.base.enums.CommonExceptionEnum;
import com.lxl.common.component.base.exception.ServiceException;
import com.lxl.common.component.base.utils.SpringContextUtils;

/**
 * IDWorker
 * 
 * @author Administrator
 *
 */
@Component
public class IdWroker {

	private static final Logger logger = LoggerFactory.getLogger(IdWroker.class);

	/**
	 * 根据table生成Id
	 * 
	 * @param tableName
	 * @return
	 * @throws ServiceException
	 */
	public static Long uuid() throws ServiceException {
		try {
			return getSnowflakeIdGenerator().nextId();
		} catch (ServiceException e) {
			logger.error("id generate error, msg={}", e.getMsg(), e);
			throw e;
		} catch (Exception e) {
			logger.error("id generate error", e);
			throw new ServiceException(CommonExceptionEnum.SYS_ERROR.getCode(), CommonExceptionEnum.SYS_ERROR.getMsg());
		}
	}

	/**
	 * 获取table对应的ID生成器
	 * 
	 * @param tableName
	 * @return
	 */
	private static SnowflakeIdGenerator getSnowflakeIdGenerator() {
		SnowflakeidConfig snowflakeidConfig = (SnowflakeidConfig) SpringContextUtils.getBean(SnowflakeidConfig.class);
		return snowflakeidConfig.getSnowflakeIdGenerator();
	}

}
