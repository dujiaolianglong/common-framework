/**
 * 
 */
package com.lxl.framework.mysql.aop;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;

import com.lxl.common.component.base.constant.CommonConstants;
import com.lxl.common.component.base.snowflake.IdWroker;
import com.lxl.common.component.base.utils.ReflectUtils;

/**
 * 对创建时间、更新时间、ID公共属性设置
 * 
 * @author Administrator
 *
 */
@Component
@Intercepts({ @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class }) })
public class MybatisInterceptor implements Interceptor {

	/**
	 * 设置Id、createTime、updateTime
	 */
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		// 获取 SQL
		SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
		// 获取参数
		Object parameter = invocation.getArgs()[1];
		// 获取私有成员变量
		List<Field> declaredFields = ReflectUtils.getField(parameter);

		for (Field field : declaredFields) {
			if (SqlCommandType.INSERT.equals(sqlCommandType)) {
				if (CommonConstants.FIELD_ID.equals(field.getName())) {
					field.setAccessible(true);
					if (null == field.get(parameter)) {
						field.set(parameter, IdWroker.uuid());
					}
				} else if (CommonConstants.FIELD_CREATE_TIME.equals(field.getName())) {
					field.setAccessible(true);
					if (null == field.get(parameter)) {
						field.set(parameter, new Date());
					}
				} else if (CommonConstants.FIELD_UPDATE_TIME.equals(field.getName())) {
					field.setAccessible(true);
					if (null == field.get(parameter)) {
						field.set(parameter, new Date());
					}
				}
			} else if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
				if (CommonConstants.FIELD_UPDATE_TIME.equals(field.getName())) {
					field.setAccessible(true);
					if (null == field.get(parameter)) {
						field.set(parameter, new Date());
					}
				}
			}
		}
		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties arg0) {

	}

}
