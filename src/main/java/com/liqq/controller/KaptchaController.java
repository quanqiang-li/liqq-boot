package com.liqq.controller;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Producer;
import com.liqq.common.Code;
import com.liqq.common.Constant;
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getKaptchaImage", method = RequestMethod.GET)
	public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response, String kaptchaKey)
			throws Exception {
		if (StringUtils.isEmpty(kaptchaKey)) {
			response.sendError(500, "验证码获取失败");
			return null;
		}
		// 自动生成验证码key
		String codeKey = Constant.KAPTCHA_CACHE_PREFIX + kaptchaKey;
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
		logger.info("图形验证码: {}={}", codeKey, capText);

		// store the text into cache
		// 有效时间 ,单位毫秒
		Integer validTime = 1000 * 300;
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
	 * @param kaptchaKey   生成验证码时的凭证
	 * @param kaptchaValue 客户端提交的验证码
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/checkKaptcha", method = RequestMethod.POST)
	@ResponseBody
	public ReturnData checkKaptcha(String kaptchaKey, String kaptchaValue, HttpServletRequest request)
			throws Exception {
		if (StringUtils.isEmpty(kaptchaKey) || StringUtils.isEmpty(kaptchaValue)) {
			return new ReturnData(Code.KAPTCHA_WRONG, null);
		}
		String codeKey = Constant.KAPTCHA_CACHE_PREFIX + kaptchaKey;
		String serverCode = sysCacheService.getKey(codeKey);
		if (kaptchaValue.equalsIgnoreCase(serverCode)) {
			return new ReturnData(Code.OK, null);
		} else {
			return new ReturnData(Code.KAPTCHA_WRONG, null);
		}

	}
}
