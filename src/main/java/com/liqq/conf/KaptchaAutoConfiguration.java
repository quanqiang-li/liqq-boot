package com.liqq.conf;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

/**
 * @ClassName: KaptchaAutoConfiguration
 * @Description: 图形验证码配置类
 *
 */
@Configuration
@ConfigurationProperties(prefix = "kaptcha")
public class KaptchaAutoConfiguration {
	/**
	 * border
	 */
	private String border;

	/**
	 * borderColor
	 */
	private String borderColor;

	/**
	 * textProducerFontColor
	 */
	private String textProducerFontColor;

	/**
	 * textProducerFontSize
	 */
	private String textProducerFontSize;

	/**
	 * textProducerFontNames
	 */
	private String textProducerFontNames;

	/**
	 * textProducerCharLength
	 */
	private String textProducerCharLength;

	/**
	 * imageWidth
	 */
	private String imageWidth;

	/**
	 * imageHeight
	 */
	private String imageHeight;

	/**
	 * noiseColor
	 */
	private String noiseColor;

	/**
	 * noiseImpl
	 */
	private String noiseImpl;

	/**
	 * obscurificatorImpl
	 */
	private String obscurificatorImpl;

	/**
	 * sessionKey
	 */
	private String sessionKey;

	/**
	 * sessionDate
	 */
	private String sessionDate;

	/**
	 * 
	 * @Description: 实例验证码图形工厂类
	 * @return 返回验证码图形工厂类
	 */
	@Bean(name = "kaptchaProducer")
	public DefaultKaptcha getKaptchaBean() {

		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		Properties properties = new Properties();
		properties.setProperty(Constants.KAPTCHA_BORDER, border);
		properties.setProperty(Constants.KAPTCHA_BORDER_COLOR, borderColor);
		properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, textProducerFontColor);
		properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, textProducerFontSize);
		properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, textProducerFontNames);
		properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, textProducerCharLength);
		properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, imageWidth);
		properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, imageHeight);
		properties.setProperty(Constants.KAPTCHA_NOISE_COLOR, noiseColor);

		properties.setProperty(Constants.KAPTCHA_NOISE_IMPL, noiseImpl);

		// 阴影渲染效果
		properties.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, obscurificatorImpl);
		properties.setProperty(Constants.KAPTCHA_SESSION_KEY, sessionKey);
		properties.setProperty(Constants.KAPTCHA_SESSION_DATE, sessionDate);
		Config config = new Config(properties);
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}

	/**
	 * @return the border
	 */
	public String getBorder() {
		return border;
	}

	/**
	 * @param border the border to set
	 */
	public void setBorder(String border) {
		this.border = border;
	}

	/**
	 * @return the borderColor
	 */
	public String getBorderColor() {
		return borderColor;
	}

	/**
	 * @param borderColor the borderColor to set
	 */
	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	/**
	 * @return the textProducerFontColor
	 */
	public String getTextProducerFontColor() {
		return textProducerFontColor;
	}

	/**
	 * @param textProducerFontColor the textProducerFontColor to set
	 */
	public void setTextProducerFontColor(String textProducerFontColor) {
		this.textProducerFontColor = textProducerFontColor;
	}

	/**
	 * @return the textProducerFontSize
	 */
	public String getTextProducerFontSize() {
		return textProducerFontSize;
	}

	/**
	 * @param textProducerFontSize the textProducerFontSize to set
	 */
	public void setTextProducerFontSize(String textProducerFontSize) {
		this.textProducerFontSize = textProducerFontSize;
	}

	/**
	 * @return the textProducerFontNames
	 */
	public String getTextProducerFontNames() {
		return textProducerFontNames;
	}

	/**
	 * @param textProducerFontNames the textProducerFontNames to set
	 */
	public void setTextProducerFontNames(String textProducerFontNames) {
		this.textProducerFontNames = textProducerFontNames;
	}

	/**
	 * @return the textProducerCharLength
	 */
	public String getTextProducerCharLength() {
		return textProducerCharLength;
	}

	/**
	 * @param textProducerCharLength the textProducerCharLength to set
	 */
	public void setTextProducerCharLength(String textProducerCharLength) {
		this.textProducerCharLength = textProducerCharLength;
	}

	/**
	 * @return the imageWidth
	 */
	public String getImageWidth() {
		return imageWidth;
	}

	/**
	 * @param imageWidth the imageWidth to set
	 */
	public void setImageWidth(String imageWidth) {
		this.imageWidth = imageWidth;
	}

	/**
	 * @return the imageHeight
	 */
	public String getImageHeight() {
		return imageHeight;
	}

	/**
	 * @param imageHeight the imageHeight to set
	 */
	public void setImageHeight(String imageHeight) {
		this.imageHeight = imageHeight;
	}

	/**
	 * @return the noiseColor
	 */
	public String getNoiseColor() {
		return noiseColor;
	}

	/**
	 * @param noiseColor the noiseColor to set
	 */
	public void setNoiseColor(String noiseColor) {
		this.noiseColor = noiseColor;
	}

	/**
	 * @return the noiseImpl
	 */
	public String getNoiseImpl() {
		return noiseImpl;
	}

	/**
	 * @param noiseImpl the noiseImpl to set
	 */
	public void setNoiseImpl(String noiseImpl) {
		this.noiseImpl = noiseImpl;
	}

	/**
	 * @return the obscurificatorImpl
	 */
	public String getObscurificatorImpl() {
		return obscurificatorImpl;
	}

	/**
	 * @param obscurificatorImpl the obscurificatorImpl to set
	 */
	public void setObscurificatorImpl(String obscurificatorImpl) {
		this.obscurificatorImpl = obscurificatorImpl;
	}

	/**
	 * @return the sessionKey
	 */
	public String getSessionKey() {
		return sessionKey;
	}

	/**
	 * @param sessionKey the sessionKey to set
	 */
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	/**
	 * @return the sessionDate
	 */
	public String getSessionDate() {
		return sessionDate;
	}

	/**
	 * @param sessionDate the sessionDate to set
	 */
	public void setSessionDate(String sessionDate) {
		this.sessionDate = sessionDate;
	}
	
	
	

}
