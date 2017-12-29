package com.hck.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hck.mapper.AlarmMapper;
import com.hck.model.Alarm;
import com.hck.model.EmailAlarm;
import com.hck.model.PageInfo;
import com.hck.model.SqlLimit;
import com.hck.model.State;

@Service
public class AlarmServiceImpl implements AlarmService {
	@Autowired
	AlarmMapper alarmMapper;

	public boolean addAlarm(Alarm alarm) throws Exception {
		String type = alarm.getClass().getSimpleName();
		System.out.println("ALarm的类型" + alarm.getClass().getSimpleName());
		switch (type) {
		case "EmailAlarm":
			return insertEmail(alarm);
		case "BlackIP":
			return insertIP(alarm);
		case "BlackDNS":
			return insertDNS(alarm);
		case "EigenAlarm":
			return insertEigen(alarm);
		}
		return false;
	}
	@Transactional
	public boolean insertEigen(Alarm alarm) throws Exception {
		int res = 0;
		res = alarmMapper.insertAlarmInfo(alarm);
		if (res <= 0)
			return false;
		alarm.setInfo_id(getLastInfoId());
		res = alarmMapper.insertEigen(alarm);
		if (res <= 0)
			return false;
		

		return true;
	}
	@Transactional
	public boolean insertDNS(Alarm alarm) throws Exception {
		int res = 0;
		res = alarmMapper.insertAlarmInfo(alarm);
		if (res <= 0)
			return false;
		alarm.setInfo_id(getLastInfoId());
		res = alarmMapper.insertDNS(alarm);
		if (res <= 0)
			return false;
		return true;
	}
	@Transactional
	public boolean insertIP(Alarm alarm) throws Exception {
		int res = 0;
		res = alarmMapper.insertAlarmInfo(alarm);
		if (res <= 0)
			return false;
		alarm.setInfo_id(getLastInfoId());
		res = alarmMapper.insertIP(alarm);
		if (res <= 0)
			return false;
		return true;
	}

	@Transactional
	public boolean insertEmail(Alarm alarm) throws Exception {
		int res = 0;
		res = alarmMapper.insertAlarmInfo(alarm);
		if (res <= 0)
			return false;
		alarm.setInfo_id(getLastInfoId());
		res = alarmMapper.insertEmail(alarm);
		if (res <= 0)
			return false;

		return true;

	}

	public int getLastInfoId() throws Exception {
		return alarmMapper.getInfoID();
	}

	/**
	 * 新增入口
	 */
	public boolean updateAlarm(Alarm alarm) {
		String type = alarm.getClass().getSimpleName();
		switch (type) {
		case "EmailAlarm":
			return updateEmail(alarm);
		case "BlackIP":
			return updateIP(alarm);
		case "BlackDNS":
			return updateDNS(alarm);
		case "EigenAlarm":
			return updateEigen(alarm);
		}
		return false;
	}

	/**
	 * 更新Eigen
	 * 
	 * @param alarm
	 * @return
	 */
	@Transactional
	private boolean updateEigen(Alarm alarm) {
		int res = 0;
		try {
			res = alarmMapper.updateEigen(alarm);
			if (res < 0) {
				return false;
			}
			res = alarmMapper.updateAlarmInfo(alarm);
			if (res < 0) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	/**
	 * 更新DNS
	 * 
	 * @param alarm
	 * @return
	 */
	@Transactional
	private boolean updateDNS(Alarm alarm) {
		int res = 0;
		try {
			res = alarmMapper.updateDNS(alarm);
			if (res < 0) {
				return false;
			}
			res = alarmMapper.updateAlarmInfo(alarm);
			if (res < 0) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 更新IP
	 * 
	 * @param alarm
	 * @return
	 */
	@Transactional
	private boolean updateIP(Alarm alarm) {
		int res = 0;
		try {
			res = alarmMapper.updateIP(alarm);
			if (res < 0) {
				return false;
			}
			res = alarmMapper.updateAlarmInfo(alarm);
			if (res < 0) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 更新邮件
	 * 
	 * @param alarm
	 * @return
	 */
	@Transactional
	private boolean updateEmail(Alarm alarm) {
		int res = 0;
		try {
			res = alarmMapper.updateEmail(alarm);
			if (res < 0) {
				return false;
			}
			res = alarmMapper.updateAlarmInfo(alarm);
			if (res < 0) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 获取列表入口
	 */
	public List<Alarm> getAlarmsByPageInfoAndTypeId(PageInfo info, int type) {
		try {
			if (type == AlarmService.EMAIL) {
				return getEmailAlarmByPageInfo(info);
			}
			if (type == AlarmService.BLACKIP) {
				return getBlackIPAlarmByPageInfo(info);
			}
			if (type == AlarmService.BLACKDNS) {
				return getBlackDNSAlarmByPageInfo(info);
			}
			if (type == AlarmService.EIGEN) {
				return getEigenAlarmByPageInfo(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public Map<Integer, String> getAlarmType() {
		Map<Integer, String> map = new HashMap();
		try {
			alarmMapper.getAlarmTypes().forEach(alarm -> map.put(alarm.getType_id(), alarm.getType_name()));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * IP分页列表
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public List<Alarm> getEigenAlarmByPageInfo(PageInfo info) throws Exception {
		SqlLimit limit = getSqlLimitByPageInfo(info);
		return alarmMapper.getEigenAlarmWithLimit(limit);
	}

	/**
	 * DNS分页列表
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public List<Alarm> getBlackDNSAlarmByPageInfo(PageInfo info) throws Exception {
		SqlLimit limit = getSqlLimitByPageInfo(info);
		return alarmMapper.getBlackDNSAlarmWithLimit(limit);
	}

	/**
	 * IP分页列表
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public List<Alarm> getBlackIPAlarmByPageInfo(PageInfo info) throws Exception {
		SqlLimit limit = getSqlLimitByPageInfo(info);
		return alarmMapper.getBlackIPAlarmWithLimit(limit);
	}

	/**
	 * 邮件分页列表
	 * 
	 * @param info
	 * @return
	 * @throws Exception
	 */
	public List<Alarm> getEmailAlarmByPageInfo(PageInfo info) throws Exception {
		SqlLimit limit = getSqlLimitByPageInfo(info);
		return alarmMapper.getEmailAlarmWithLimit(limit);
	}

	public SqlLimit getSqlLimitByPageInfo(PageInfo info) {
		int offset = 0;
		int rowcount = info.getPageSize();
		if (info.getPageNow() - 1 < 0) {
			offset = 0;
		} else {
			offset = (info.getPageNow() - 1) * info.getPageSize();
		}
		return new SqlLimit(offset, rowcount);
	}

	@Override
	public int getAlarmCountByType(int type) {
		try {
			if (type == AlarmService.EMAIL) {
				return getEmailCount();
			}
			if (type == AlarmService.BLACKIP) {
				return getIPCount();
			}
			if (type == AlarmService.BLACKDNS) {
				return getDNSCount();
			}
			if (type == AlarmService.EIGEN) {
				return getEigenCount();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}

	public int getEigenCount() {
		try {
			return alarmMapper.getEigenRowCount();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getDNSCount() {
		try {
			return alarmMapper.getDNSRowCount();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getIPCount() {
		try {
			return alarmMapper.getIPRowCount();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getEmailCount() {
		try {
			return alarmMapper.getEmailRowCount();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public boolean deleteAlarmById(String type,int id) {
		switch(type) {
			case "email" : return deleteEmail(id);
			case "ip" : return deleteIP(id);
			case "dns" : return deleteDNS(id);
			case "eigen" : return deleteEigen(id);
	}
		return false;
	}

	private boolean deleteInfo(int id) throws Exception {
		int res = alarmMapper.deleteAlarmInfoById(id);
		return res > 0 ? true:false;
	}
	
	private boolean deleteEigen(int id) {
		try {
			if(!deleteInfo(id)) 
				return false;
			if(alarmMapper.deleteEigenById(id) <= 0)
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean deleteDNS(int id) {
		try {
			if(!deleteInfo(id)) 
				return false;
			if(alarmMapper.deleteDNSById(id) <= 0)
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean deleteIP(int id) {
		try {
			if(!deleteInfo(id)) 
				return false;
			if(alarmMapper.deleteIPById(id) <= 0)
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean deleteEmail(int id) {
		try {
			if(!deleteInfo(id)) 
				return false;
			if(alarmMapper.deleteEmailById(id) <= 0)
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	

}
