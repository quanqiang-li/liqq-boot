package com.liqq.common;

/**
 * 定义常量数据,避免魔法数 final 不允许继承修改
 * 
 * @author liqq
 *
 */
public final class Constant {

	public static final String CHARSET_UTF8 = "UTF-8";

	/**
	 * 登陆缓存前缀
	 */
	public static final String LOGIN_CACHE_PREFIX = "login:";
	/**
	 * 验证码缓存前缀
	 */
	public static final String KAPTCHA_CACHE_PREFIX = "kaptcha:";
}
