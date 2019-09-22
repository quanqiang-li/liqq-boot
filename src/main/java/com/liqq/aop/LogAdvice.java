package com.liqq.aop;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.liqq.annotation.LogAnnotation;
import com.liqq.common.Constant;
import com.liqq.model.SysLog;
import com.liqq.service.SysLogService;
import com.liqq.util.WebUtil;

import io.swagger.annotations.ApiOperation;

/**
 * 日志注解处理
 * 
 * @author liqq
 *
 */
@Aspect
@Component
public class LogAdvice {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysLogService sysLogService;

	/**
	 * 围绕注解
	 * 
	 * @param joinPoint
	 */
	@Around(value = "@annotation(com.liqq.annotation.LogAnnotation)")
	public Object logSave(ProceedingJoinPoint joinPoint) {
		logger.info("记录日志");
		Date start = new Date();
		long time = 0;
		Object res = null;
		byte status = 0;
		String remark = null;
		String ip = null;
		Integer userId = null;
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Object[] args = joinPoint.getArgs();
		String method = signature.getDeclaringTypeName() + "." + signature.getName();
		LogAnnotation annotation = signature.getMethod().getAnnotation(LogAnnotation.class);
		String module = annotation.module();
		// 当LogAnnotation注解没有指定module时,可以尝试从swagger的ApiOperation注解获取value
		if(StringUtils.isEmpty(module)){
			ApiOperation apiOperation = signature.getMethod().getAnnotation(ApiOperation.class);
			module = apiOperation==null?null:apiOperation.value();
		}
		SysLog sysLog = new SysLog();

		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
					.getRequest();
			ip = WebUtil.getIpAddr(request);
			res = joinPoint.proceed();
			time = System.currentTimeMillis() - start.getTime();
			userId = (Integer) request.getAttribute(Constant.USER_ID);
		} catch (Throwable e) {
			status = 1;
			remark = e.getMessage();
			e.printStackTrace();
		} finally {
			sysLog.setArgs(JSON.toJSONString(args));// 入参
			sysLog.setCreateTime(start);
			sysLog.setMethod(method);
			sysLog.setModule(module); // 模块
			sysLog.setStatus(status); // 状态
			sysLog.setReturnValue(JSON.toJSONString(res)); // 然后结果
			sysLog.setRemark(remark);
			sysLog.setRunTime(time);
			sysLog.setClientIp(ip);
			sysLog.setUserId(userId);
			sysLogService.save(sysLog);
		}
		return res;
	}
}
