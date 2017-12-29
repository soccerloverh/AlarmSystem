package com.hck.service;

import java.util.List;

import com.hck.model.PageInfo;
import com.hck.model.User;
import com.hck.model.UserSearchCondition;

public interface UserService {
	
	public int getUserCountWithCondition(User user) throws Exception ;
	
	/**
	 * 条件查询
	 * @return
	 * @throws Exception
	 */
	public List<User> getUserWithCondition(User user,PageInfo info)throws Exception;
	
	/**
	 * 判断User是否合法
	 * @param user
	 * @return
	 */
	public User isLegal(User user) throws Exception;
	
	/**
	 * 得到一个User对象
	 * @param user_id
	 * @return
	 */
	public User getUser(int user_id) throws Exception;
	
	/**
	 * 根据当前页获取User列表
	 * @param page_now
	 * @return
	 */
	public List<User> getUsersOnPage(PageInfo info) throws Exception;
	
	/**
	 * Get the number of Users
	 * @return
	 */
	public int getUserCount()  throws Exception;
	
	/**
	 * 添加一个用户
	 * @param user
	 * @return
	 */
	public boolean addUser(User user)  throws Exception;
	
	/**
	 * 更新一个User
	 * @return
	 */
	public boolean updateUser(User user)  throws Exception;
	
	/**
	 * 删除一个User
	 * @param id
	 * @return
	 */
	public boolean deleteUser(int id)  throws Exception;
}
