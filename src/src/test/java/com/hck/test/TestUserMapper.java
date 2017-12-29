package com.hck.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hck.mapper.UserMapper;
import com.hck.model.PageInfo;
import com.hck.model.SqlLimit;
import com.hck.model.User;
import com.hck.model.UserSearchCondition;

public class TestUserMapper {
	
	private ApplicationContext context;
	
	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("spring/ApplicationContext.xml");
	}
	
	@Test
	public void testGetUsers() throws Exception {
		UserMapper userMapper = context.getBean(UserMapper.class);
		SqlLimit limit = new SqlLimit(4,3);
		List<User> list = userMapper.getUsersWithLimit(limit);
		System.out.println(list);
	}
	
	@Test
	public void testGetUsers1() throws Exception {
		UserMapper userMapper = context.getBean(UserMapper.class);
		User user = new User();
		user.setUser_name("%te%");
		user.setUser_level(1);
		PageInfo info = new PageInfo();
		info.setPageNow(1);
		info.setPageSize(4);
		UserSearchCondition condition = getUserCondition(user, info);
		List<User> list = userMapper.getUserWithCondition(condition);
		System.out.println(list);
	}
	
	public UserSearchCondition getUserCondition(User user,PageInfo info) throws Exception {
		int offset = 0;
		int rowcount = info.getPageSize();
		if(info.getPageNow()-1 < 0) {
			offset = 0;
		}else{
			offset = (info.getPageNow()-1)*info.getPageSize();
		}
		UserSearchCondition condition = new UserSearchCondition();
		condition.setUser_name(user.getUser_name());
		condition.setUser_level(user.getUser_level());
		condition.setLimit(new SqlLimit(offset,rowcount));
		return condition;
	}
}
