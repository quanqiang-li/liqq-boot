package com.liqq.controller;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Producer;
import com.liqq.common.Code;
import com.liqq.common.ReturnData;
import com.liqq.service.SysCacheService;

/**
 * 图形验证码
 * 
 * @ClassName: KaptchaController
 *
 */
@Controller
@RequestMapping("/kaptcha")
public class KaptchaController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final static String CACHE_PREFIX = "kaptcha:";

	/**
	 * kaptchaProducer
	 */
	@Autowired
	private Producer kaptchaProducer;

	@Autowired
	private SysCacheService sysCacheService;

	/**
	 * 获取验证码图片
	 * 
	 * @param codeKey
	 *            前端生成的key,后面校验用来比对的凭证
	 * @param response
	 * @param validTime
	 *            有效时间 ,单位毫秒
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getKaptchaImage", method = RequestMethod.GET)
	public ModelAndView getKaptchaImage(String codeKey, HttpServletResponse response, Integer validTime) throws Exception {
		codeKey = CACHE_PREFIX + codeKey;
		response.setDateHeader("Expires", 0);

		// Set standard HTTP/1.1 no-cache headers.
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

		// Set IE extended HTTP/1.1 no-cache headers (use addHeader).
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");

		// Set standard HTTP/1.0 no-cache header.
		response.setHeader("Pragma", "no-cache");

		// return a jpeg
		response.setContentType("image/jpeg");

		// create the text for the image
		String capText = kaptchaProducer.createText();
		logger.info("图形验证码: {}={}", codeKey,capText);

		// store the text into cache
		sysCacheService.writeValue(codeKey, capText, validTime);
		// create the image with the text
		BufferedImage bi = kaptchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();

		// write the data out
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 检验图片验证码
	 * 
	 * @param clientCode
	 *            客户端提交的验证码
	 * @param codeKey
	 *            生成验证码时的凭证
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkKaptcha", method = RequestMethod.GET)
	@ResponseBody
	public ReturnData checkKaptcha(String clientCode, String codeKey) throws Exception {
		codeKey = CACHE_PREFIX + codeKey;
		String serverCode = sysCacheService.getKey(codeKey);
		if (clientCode != null && clientCode.equalsIgnoreCase(serverCode)) {
			return new ReturnData(Code.OK, null);
		} else {
			return new ReturnData(Code.KAPTCHA_WRONG, null);
		}

	}
}
