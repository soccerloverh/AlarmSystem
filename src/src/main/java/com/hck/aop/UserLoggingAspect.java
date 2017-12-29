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
import com.hck.model.State;
import com.hck.model.User;

@Component
@Aspect
public class UserLoggingAspect {
	@Autowired
	LogMapper mapper;
	
	public UserLoggingAspect() {
	}
	//日志:登录
	@AfterReturning(value = "execution(* com.hck.handler.UserHandler.loginHandle(..))",returning="res")
	public void loginLog(JoinPoint jp,Object res) {
		State state = (State)res;
		Object[] objs = jp.getArgs();
		User user = (User) objs[0];
		HttpServletRequest request = (HttpServletRequest) objs[1];
		if(state.getStatus().equals(State.OK)) {
			System.out.println("用户："+user.getUser_name()+" -- 登录系统 -- 时间:"+new Date()+" -- IP:"+request.getRemoteAddr());
			Log log = new Log();
			log.setLog_userName(user.getUser_name());
			log.setLog_operation("登录系统");
			log.setLog_ip(request.getRemoteAddr());
			log.setLog_time(System.currentTimeMillis());
			mapper.log(log);
		}
	}
	//日志:访问主页
	@AfterReturning(value="execution(* com.hck.handler.UserHandler.toMainPageHandle(*))")
	public void mainPageLog(JoinPoint jp) {
		Object[] objs = jp.getArgs();
		HttpServletRequest request = (HttpServletRequest) objs[0];
		User user = (User) request.getSession().getAttribute("currentUser");
		System.out.println("用户："+user.getUser_name()+" -- 访问主页 -- 时间:"+new Date()+" -- IP:"+request.getRemoteAddr());
		Log log = new Log();
		log.setLog_userName(user.getUser_name());
		log.setLog_operation("访问主页");
		log.setLog_ip(request.getRemoteAddr());
		log.setLog_time(System.currentTimeMillis());
		mapper.log(log);
	}
	
	//日志:访问用户管理页面
	@AfterReturning(value="execution(* com.hck.handler.UserHandler.toUsersConfigeration(*))")
	public void userConfigerLog(JoinPoint jp) {
		Object[] objs = jp.getArgs();
		HttpServletRequest request = (HttpServletRequest) objs[0];
		User user = (User) request.getSession().getAttribute("currentUser");
		System.out.println("用户："+user.getUser_name()+" -- 访问用户管理页面 -- 时间:"+new Date()+" -- IP:"+request.getRemoteAddr());
		Log log = new Log();
		log.setLog_userName(user.getUser_name());
		log.setLog_operation("访问用户管理页面");
		log.setLog_ip(request.getRemoteAddr());
		log.setLog_time(System.currentTimeMillis());
		mapper.log(log);
	}
	
	//日志:添加用户
	@AfterReturning(value="execution(* com.hck.handler.UserHandler.addUser(..))",returning="res")
	public void insertUserLog(JoinPoint jp,Object res) {
		State state = (State)res;
		Object[] objs = jp.getArgs();
		User user = (User) objs[0];
		HttpServletRequest request = (HttpServletRequest) objs[1];
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		Log log = new Log();
		log.setLog_userName(currentUser.getUser_name());
		log.setLog_ip(request.getRemoteAddr());
		log.setLog_time(System.currentTimeMillis());
		if(state.getStatus().equals(State.OK)) {
			System.out.println("用户："+currentUser.getUser_name()+" -- 添加新用户:"+user.getUser_name()+"成功-- 时间:"+new Date()+" -- IP:"+request.getRemoteAddr());
			log.setLog_operation("添加新用户:"+user.getUser_name()+"成功");
		}else {
			System.out.println("用户："+currentUser.getUser_name()+" -- 添加新用户:"+user.getUser_name()+"失败-- 时间:"+new Date()+" -- IP:"+request.getRemoteAddr());
			log.setLog_operation("添加新用户:"+user.getUser_name()+"失败");
		}
		mapper.log(log);
	}
	//日志:更新用户
	@AfterReturning(value="execution(* com.hck.handler.UserHandler.updateUser(..))",returning="res")
	public void updateUserLog(JoinPoint jp,Object res) {
		State state = (State)res;
		Object[] objs = jp.getArgs();
		User user = (User) objs[0];
		HttpServletRequest request = (HttpServletRequest) objs[1];
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		Log log = new Log();
		log.setLog_userName(currentUser.getUser_name());
		log.setLog_ip(request.getRemoteAddr());
		log.setLog_time(System.currentTimeMillis());
		if(state.getStatus().equals(State.OK)) {
			System.out.println("用户："+currentUser.getUser_name()+" -- 更新用户:"+user.getUser_name()+"成功-- 时间:"+new Date()+" -- IP:"+request.getRemoteAddr());
			log.setLog_operation("更新用户:"+user.getUser_name()+"成功");
		}else {
			System.out.println("用户："+currentUser.getUser_name()+" -- 更新用户:"+user.getUser_name()+"失败-- 时间:"+new Date()+" -- IP:"+request.getRemoteAddr());
			log.setLog_operation("更新用户:"+user.getUser_name()+"失败");
		}
		mapper.log(log);
	}
	//日志:删除用户
	@AfterReturning(value="execution(* com.hck.handler.UserHandler.deleteUser(..))",returning="res")
	public void deleteUserLog(JoinPoint jp,Object res) {
		State state = (State)res;
		Object[] objs = jp.getArgs();
		int delete_id = (int) objs[0];
		HttpServletRequest request = (HttpServletRequest) objs[1];
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		Log log = new Log();
		log.setLog_userName(currentUser.getUser_name());
		log.setLog_ip(request.getRemoteAddr());
		log.setLog_time(System.currentTimeMillis());
		if(state.getStatus().equals(State.OK)) {
			System.out.println("用户："+currentUser.getUser_name()+" -- 删除用户,ID:"+delete_id+"成功-- 时间:"+new Date()+" -- IP:"+request.getRemoteAddr());
			log.setLog_operation("删除用户,ID:"+delete_id+"成功");
		}else {
			System.out.println("用户："+currentUser.getUser_name()+" -- 删除用户,ID:"+delete_id+"失败-- 时间:"+new Date()+" -- IP:"+request.getRemoteAddr());
			log.setLog_operation("删除用户,ID:"+delete_id+"失败");
		}
		mapper.log(log);
	}
}
