package com.hck.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hck.mapper.UserMapper;
import com.hck.model.PageInfo;
import com.hck.model.SqlLimit;
import com.hck.model.User;
import com.hck.model.UserSearchCondition;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserMapper userMapper;
	
	public User isLegal(User user) {
		try {
			return userMapper.getUserByNameAndPassword(user);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public User getUser(int user_id) throws Exception {
		return userMapper.getUserById(user_id);
	}

	public List<User> getUsersOnPage(PageInfo info) throws Exception{
		int offset = 0;
		int rowcount = info.getPageSize();
		if(info.getPageNow()-1 < 0) {
			offset = 0;
		}else{
			offset = (info.getPageNow()-1)*info.getPageSize();
		}
		return userMapper.getUsersWithLimit(new SqlLimit(offset, rowcount));
	}

	public int getUserCount() throws Exception {
		return userMapper.getUsersCount();
	}

	public boolean addUser(User user) {
		int res = 0;
		try {
		  res = userMapper.addUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return res > 0?true:false;
	}

	public boolean updateUser(User user) {
		int res = 0;
		try {
		  res = userMapper.updateUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return res > 0?true:false;
	}

	public boolean deleteUser(int id) {
		int res = 0;
		try {
		  res = userMapper.deleteUserById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return res > 0?true:false;
	}

	@Override
	public List<User> getUserWithCondition(User user,PageInfo info) throws Exception {
		UserSearchCondition condition = getUserCondition(user, info);
		System.out.println("Condition:"+condition);
		
		return userMapper.getUserWithCondition(condition);
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

	@Override
	public int getUserCountWithCondition(User user) throws Exception {
		UserSearchCondition condition = new UserSearchCondition();
		condition.setUser_name(user.getUser_name());
		condition.setUser_level(user.getUser_level());
		return userMapper.getUsersCountWithCondition(condition);
	}
}
