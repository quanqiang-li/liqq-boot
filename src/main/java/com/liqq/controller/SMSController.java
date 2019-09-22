package com.liqq.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liqq.common.Code;
import com.liqq.common.Constant;
import com.liqq.common.ReturnData;
import com.liqq.service.SMSService;
import com.liqq.service.SysCacheService;

/**
 * 短信验证码
 * 
 * @ClassName: smsController
 *
 */
@Controller
@RequestMapping("/sms")
public class SMSController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * smsService
	 */
	@Autowired
	private SMSService smsService;

	@Autowired
	private SysCacheService sysCacheService;

	/**
	 * 获取短信验证码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getSMSCode", method = RequestMethod.GET)
	@ResponseBody
	public ReturnData getSMSCode(String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return new ReturnData(Code.PARAM_ERROR, null);
		}
		String smsCode = smsService.sendSMSCode(mobile);
		logger.info("发送短信验证码{}={}",mobile,smsCode);
		return new ReturnData(Code.OK, null);
	}

	/**
	 * 检验短信验证码
	 * @param mobile
	 * @param smsCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkSMSCode", method = RequestMethod.POST)
	@ResponseBody
	public ReturnData checkKaptcha(String mobile, String smsCode)
			throws Exception {
		if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(smsCode)) {
			return new ReturnData(Code.PARAM_ERROR, null);
		}
		String codeKey = Constant.SMS_PREFIX + mobile;
		String serverCode = sysCacheService.getKey(codeKey);
		if (smsCode.equalsIgnoreCase(serverCode)) {
			return new ReturnData(Code.OK, null);
		} else {
			return new ReturnData(Code.SMS_WRONG, null);
		}

	}
}
