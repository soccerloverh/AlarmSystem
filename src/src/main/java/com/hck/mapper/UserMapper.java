package com.hck.mapper;

import java.util.List;

import com.hck.model.SqlLimit;
import com.hck.model.User;
import com.hck.model.UserSearchCondition;

public interface UserMapper {
	public User getUserById(int user_id) throws Exception;
	
	public List<User> getUsersWithLimit(SqlLimit limit) throws Exception;
	
	public User getUserByNameAndPassword(User user) throws Exception;
	
	public int getUsersCount() throws Exception;
	
	public int updateUser(User user) throws Exception;
	
	public int deleteUserById(int id) throws Exception;
	
	public int addUser(User user) throws Exception;
	
	public List<User> getUserWithCondition(UserSearchCondition condition) throws Exception;
	
	public int getUsersCountWithCondition(UserSearchCondition condition) throws Exception;
}
