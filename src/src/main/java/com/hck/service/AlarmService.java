package com.hck.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hck.model.Alarm;
import com.hck.model.AlarmType;
import com.hck.model.PageInfo;
@Service
public interface AlarmService {
	
	public static final int EMAIL=1;
	public static final int BLACKIP=2;
	public static final int BLACKDNS=3;
	public static final int EIGEN=4;
	/**
	 * 根据页面信息和需要获取的警报类型获取警报列表
	 * @param info
	 * @param type
	 * @return
	 */
	public List<Alarm> getAlarmsByPageInfoAndTypeId(PageInfo info,int type);
	
	/**
	 * 更新一个警报
	 * @param alarm
	 * @return
	 */
	public boolean updateAlarm(Alarm alarm);
	
	/**
	 * 添加一个警报
	 * @param alarm
	 * @return
	 * @throws Exception
	 */
	public boolean addAlarm(Alarm alarm) throws Exception;
	
	/**
	 * 根据ID删除一个警报
	 * @param type
	 * @param id
	 * @return
	 */
	public boolean deleteAlarmById(String type ,int id);
	
	/**
	 * 获取警报类型
	 * @return
	 */
	public Map<Integer,String> getAlarmType();
	
	/**
	 * 获取type类型警报所有记录条数
	 * @param type
	 * @return
	 */
	public int getAlarmCountByType(int type);

}
