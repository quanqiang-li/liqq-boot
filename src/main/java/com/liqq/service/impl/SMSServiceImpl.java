package com.liqq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liqq.common.Constant;
import com.liqq.service.SMSService;
import com.liqq.service.SysCacheService;
import com.liqq.util.JavaSmsApi;

@Service
public class SMSServiceImpl implements SMSService {

	@Autowired
	private SysCacheService sysCacheService;

	@Override
	public String sendSMSCode(String mobile) {
		String smsCode = (int) ((Math.random() * 9 + 1) * 100000) + "";
		sysCacheService.writeValue(Constant.SMS_PREFIX + mobile, smsCode, 120 * 1000);
		JavaSmsApi.tplSendSms("1270091", "#code#="+smsCode, mobile);
		return smsCode;

	}

	public static void main(String[] args) {
		String smsCode = (int) ((Math.random() * 9 + 1) * 100000) + "";
		System.out.println(smsCode);
	}
}
