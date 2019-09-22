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
	/**
	 * 短信前缀sms
	 */
	public static final String SMS_PREFIX = "sms:";
	/**
	 * token参数
	 */
	public static final String TOKEN_PARAM = "token";
	/**
	 * sysuser id 属性
	 */
	public static final String USER_ID = "userId";
	/**
	 * sysuser id 属性
	 */
	public static final Integer PAGE_SIZE = 10;
}
