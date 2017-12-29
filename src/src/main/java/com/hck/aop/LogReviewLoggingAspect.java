package com.hck.aop;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hck.mapper.LogMapper;
import com.hck.model.Log;
import com.hck.model.User;

@Component
@Aspect
public class LogReviewLoggingAspect {
	@Autowired
	LogMapper mapper;
	
	@AfterReturning(value="execution(* com.hck.handler.LogHandler.toLoggingPage(..))")
	public void toLogReviewPageLog(JoinPoint jp) {
		Object objs[] = jp.getArgs();
		HttpServletRequest request = (HttpServletRequest) objs[0];
		User user = (User) request.getSession().getAttribute("currentUser");
		Log log = new Log();
		System.out.println("用户："+user.getUser_name()+" -- 访问日志审计 -- 时间:"+new Date()+" -- IP:"+request.getRemoteAddr());
		log.setLog_ip(request.getRemoteAddr());
		log.setLog_operation("访问日志审计");
		log.setLog_time(System.currentTimeMillis());
		log.setLog_userName(user.getUser_name());
		mapper.log(log);
	}
}
