package com.hck.handler;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hck.model.PageInfo;
import com.hck.model.State;
import com.hck.model.User;
import com.hck.service.UserService;

@Controller
public class UserHandler {
	final int PAGESIZE = 4;
	
	@Autowired
	UserService userService;
	
	@RequestMapping("getSearchSplit")
	@ResponseBody
	public PageInfo getSearchSplit(@RequestBody User user) {
		PageInfo info = new PageInfo();
		try {
			int rowcount = userService.getUserCountWithCondition(user);
			info.setPageSize(PAGESIZE);
			info.setRowCount(rowcount);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return info;
	}
	
	@RequestMapping("getSearchUser/{pageNow}")
	@ResponseBody
	public List<User> getSearchUser(@RequestBody User user,@PathVariable int pageNow){
		PageInfo info = new PageInfo();
		info.setPageNow(pageNow);
		info.setPageSize(PAGESIZE);
		System.out.println("前端传来的查询条件"+user.getUser_name()+":"+user.getUser_level());
		List<User> users = null;
		try {
			users =  userService.getUserWithCondition(user, info);
			System.out.println(users);
			return users;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("addUser")
	@ResponseBody
	public State addUser(@RequestBody User user,HttpServletRequest request) {
		State info = new State();
		try {
			if(userService.addUser(user)) {
				info.setStatus(State.OK);
				return info;
			};
		} catch (Exception e) {
			e.printStackTrace();
			info.setStatus(State.ERRO);
			info.setInfo("Add Erro");
			return info;
		}
		info.setStatus(State.ERRO);
		info.setInfo("Add Failed");
		return info;
	}
	
	//登录
	@RequestMapping(value="login")
	@ResponseBody
	public State loginHandle(@RequestBody User user,HttpServletRequest request) {
		User u = null;
		try {
			u = userService.isLegal(user);
			System.out.println(u);
			if(u == null) {
				return new State(State.ERRO,null,"User name or password incorrect");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new State(State.ERRO, null, "Illegal user name or password");
		}
		System.out.println("----Login OK-----");
		request.getSession().setAttribute("currentUser", u);
		return new State(State.OK, "toMainPage", "Login Success");
	}
	
	//主页面跳转
	@RequestMapping("toMainPage")
	public String toMainPageHandle(HttpServletRequest request) {
		return "main";
	}
	
	//异步获取用户列表
	@RequestMapping("getUsersListByPage/{pageNow}")
	@ResponseBody
	public List<User> getUserListByPage(@PathVariable Integer pageNow) {
		PageInfo info = new PageInfo();
		info.setPageNow(pageNow);
		info.setPageSize(PAGESIZE);
		List<User> list = null;
		try {
			list = userService.getUsersOnPage(info);
		} catch (Exception e) {
			e.printStackTrace();
			return list;
		}
		return list;
	}
	@RequestMapping("getSplitInfo")
	@ResponseBody
	public PageInfo getSplitInfo() {
		PageInfo info = new PageInfo();
		try {
			info.setPageSize(PAGESIZE);
			info.setRowCount(userService.getUserCount());
			
		} catch (Exception e) {
			return null;
		}
		
		return info;
	}
	
	//用户管理页面跳转
	@RequestMapping("UsersConfigeration")
	public String toUsersConfigeration(HttpServletRequest request) {
		return "UsersConfigeration";
	}
	
	//更新用户
	@RequestMapping("updateUser")
	@ResponseBody
	public State updateUser(@RequestBody User user,HttpServletRequest request) {
		State info = new State();
		try {
			if(userService.updateUser(user)) {
				info.setStatus(State.OK);
				return info;
			};
		} catch (Exception e) {
			e.printStackTrace();
			info.setStatus(State.ERRO);
			info.setInfo("Update Erro");
			return info;
		}
		info.setStatus(State.ERRO);
		info.setInfo("Update Failed");
		return info;
	}
	
	@RequestMapping("deleteUser/{id}")
	@ResponseBody
	public State deleteUser(@PathVariable int id,HttpServletRequest request) {
		State info = new State();
		try {
			if(userService.deleteUser(id)) {
				info.setStatus(State.OK);
				return info;
			};
		} catch (Exception e) {
			e.printStackTrace();
			info.setStatus(State.ERRO);
			info.setInfo("Delete Erro");
			return info;
		}
		info.setStatus(State.ERRO);
		info.setInfo("Delete Failed");
		return info;
	}
	
}
