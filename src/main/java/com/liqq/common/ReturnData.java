package com.liqq.common;

/**
 * 通用的返回数据,json序列化之后传递
 * 
 * @author liqq
 *
 */
public class ReturnData {

	private int code;
	private String value;
	private Object data;

	public ReturnData(Code code, Object data) {
		this.code = code.key();
		this.value = code.value();
		this.data = data;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the data
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data
	 *            the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}

}
