/**
 * 
 */
package com.lxl.common.component.base.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 请求包装类，避免流只读一次后不能用情况
 * 
 * @author Administrator
 *
 */
public class RequestWrapper extends HttpServletRequestWrapper {
	private final byte[] body;

	public RequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		String bodyString = HttpHelper.getBodyString(request);
		body = bodyString.getBytes(Charset.forName("UTF-8"));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(body);

		return new ServletInputStream() {
			@Override
			public int read() throws IOException {
				return bais.read();
			}

			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
			}
		};
	}
}
