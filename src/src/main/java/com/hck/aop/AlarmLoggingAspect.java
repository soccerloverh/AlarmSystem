package com.hck.aop;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import com.hck.mapper.LogMapper;
import com.hck.model.Alarm;
import com.hck.model.Log;
import com.hck.model.State;
import com.hck.model.User;

@Component
@Aspect
@EnableAspectJAutoProxy(exposeProxy=true,proxyTargetClass=true)
public class AlarmLoggingAspect {
	@Autowired
	LogMapper mapper;

	// 日志：删除Alarm
	@AfterReturning(value = "execution(* com.hck.handler.AlarmHandler.delete(..))", returning = "res")
	public void deleteLog(JoinPoint jp, Object res) {
		State state = (State) res;
		Object[] objs = jp.getArgs();
		String type = (String) objs[0];
		int id = (int) objs[1];
		HttpServletRequest request = (HttpServletRequest) objs[2];
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		Log log = new Log();
		log.setLog_userName(currentUser.getUser_name());
		log.setLog_ip(request.getRemoteAddr());
		log.setLog_time(System.currentTimeMillis());
		if (state.getStatus().equals(State.OK)) {
			System.out.println("用户：" + currentUser.getUser_name() + " -- 删除" + type + " ID:" + id + " 成功-- 时间:"
					+ new Date() + " -- IP:" + request.getRemoteAddr());
			log.setLog_operation("用户：" + currentUser.getUser_name() + "删除" + type + " ID:" + id + " 成功");
		} else {
			System.out.println("用户：" + currentUser.getUser_name() + " -- 删除" + type + " ID:" + id + " 失败-- 时间:"
					+ new Date() + " -- IP:" + request.getRemoteAddr());
			log.setLog_operation("用户：" + currentUser.getUser_name() + "删除" + type + " ID:" + id + " 失败");
		}
		mapper.log(log);
	}

	// 日志：新增Alarm
	@AfterReturning(value = "execution(* com.hck.handler.AlarmHandler.insert(..))", returning = "res")
	public void insertLog(JoinPoint jp, Object res) {
		State state = (State) res;
		Object[] objs = jp.getArgs();
		Alarm alarm = (Alarm) objs[0];
		HttpServletRequest request = (HttpServletRequest) objs[1];
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		Log log = new Log();
		log.setLog_userName(currentUser.getUser_name());
		log.setLog_ip(request.getRemoteAddr());
		log.setLog_time(System.currentTimeMillis());
		if (state.getStatus().equals(State.OK)) {
			System.out.println("用户：" + currentUser.getUser_name() + " -- 新增" + alarm.getClass().getSimpleName() + " Name:"
							+ alarm.getAlarm_name() + " 成功-- 时间:" + new Date() + " -- IP:" + request.getRemoteAddr());
			log.setLog_operation("用户：" + currentUser.getUser_name() + "新增" + alarm.getClass().getSimpleName() + " Name:"
					+ alarm.getAlarm_name() + " 成功");
		} else {
			System.out.println("用户：" + currentUser.getUser_name() + " -- 新增" + alarm.getClass().getSimpleName() + " Name:"
							+ alarm.getAlarm_name() + " 失败-- 时间:" + new Date() + " -- IP:" + request.getRemoteAddr());
			log.setLog_operation("用户：" + currentUser.getUser_name() + "新增" + alarm.getClass().getSimpleName() + " Name:"
					+ alarm.getAlarm_name() + " 失败");
		}
		mapper.log(log);
	}

	// 日志：更新Alarm
	@AfterReturning(value = "execution(* com.hck.handler.AlarmHandler.update(..))", returning = "res")
	public void updateLog(JoinPoint jp, Object res) {
		State state = (State) res;
		Object[] objs = jp.getArgs();
		Alarm alarm = (Alarm) objs[0];
		HttpServletRequest request = (HttpServletRequest) objs[1];
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		Log log = new Log();
		log.setLog_userName(currentUser.getUser_name());
		log.setLog_ip(request.getRemoteAddr());
		log.setLog_time(System.currentTimeMillis());
		if (state.getStatus().equals(State.OK)) {
			/*控制台日志*/
			System.out.println("用户：" + currentUser.getUser_name() + " -- 更新" + alarm.getClass().getSimpleName() + " ID:"
					+ alarm.getInfo_id() + " 成功-- 时间:" + new Date() + " -- IP:" + request.getRemoteAddr());
			/*数据库日志*/
			log.setLog_operation("用户：" + currentUser.getUser_name() + "更新" + alarm.getClass().getSimpleName() + " ID:"
					+ alarm.getInfo_id() + " 成功");
		} else {
			/*控制台日志*/
			System.out.println("用户：" + currentUser.getUser_name() + " -- 更新" + alarm.getClass().getSimpleName() + " ID:"
					+ alarm.getInfo_id() + " 失败-- 时间:" + new Date() + " -- IP:" + request.getRemoteAddr());
			/*数据库日志*/
			log.setLog_operation("用户：" + currentUser.getUser_name() + "更新" + alarm.getClass().getSimpleName() + " ID:"
					+ alarm.getInfo_id() + " 失败");
		}
		mapper.log(log);
	}
	
	@AfterReturning(value="execution(* com.hck.handler.AlarmHandler.toAlarmConfigerPage(..))")
	public void toAlarmConfigerPageLog(JoinPoint jp){
		Object[] objs = jp.getArgs();
		HttpServletRequest request = (HttpServletRequest) objs[0];
		User user = (User) request.getSession().getAttribute("currentUser");
		/*控制台日志*/
		System.out.println("用户："+user.getUser_name()+" -- 访问警报配置页面 -- 时间:"+new Date()+" -- IP:"+request.getRemoteAddr());
		/*数据库日志*/
		Log log = new Log();
		log.setLog_userName(user.getUser_name());
		log.setLog_operation("访问警报配置页面");
		log.setLog_ip(request.getRemoteAddr());
		log.setLog_time(System.currentTimeMillis());
		mapper.log(log);
	}

	
}
