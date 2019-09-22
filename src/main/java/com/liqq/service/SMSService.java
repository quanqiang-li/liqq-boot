package com.liqq.service;
/**
 * Short Messaging Service 短信服务
 * @author Administrator
 *
 */
public interface SMSService {

	/**
	 * 发送短信验证码
	 * @param mobile
	 * @return 短信验证码
	 */
	public String sendSMSCode(String mobile);
}
