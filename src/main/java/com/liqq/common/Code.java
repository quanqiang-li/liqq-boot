package com.liqq.common;

public enum Code {

	OK(0, "成功"), KAPTCHA_WRONG(1001, "验证码错误"), AUTH_ERROR(2000, "认证失败"), ACCESS_ERROR(2001, "授权失败"),
	GLOBAL_ERROR(9000, "全局异常"), SYS_ERROR(9999, "系统异常");
	private int key;
	private String value;

	private Code(int key, String value) {
		this.key = key;
		this.value = value;
	}

	public int key() {
		return this.key;
	}

	public String value() {
		return this.value;
	}

}
