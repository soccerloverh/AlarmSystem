package com.hck.mapper;

import java.util.List;
import com.hck.model.Alarm;
import com.hck.model.AlarmType;
import com.hck.model.SqlLimit;

public interface AlarmMapper {
	List<Alarm> getEmailAlarmWithLimit(SqlLimit limit) throws Exception;
	
	List<Alarm> getBlackIPAlarmWithLimit(SqlLimit limit)throws Exception;
	
	List<Alarm> getBlackDNSAlarmWithLimit(SqlLimit limit)throws Exception;
	
	List<Alarm> getEigenAlarmWithLimit(SqlLimit limit)throws Exception;
	
	List<AlarmType> getAlarmTypes()throws Exception;
	
	int getEmailRowCount() throws Exception;
	
	int getIPRowCount() throws Exception;
	
	int getDNSRowCount() throws Exception;
	
	int getEigenRowCount() throws Exception;
	
	int updateEmail(Alarm alarm) throws Exception;
	
	int updateIP(Alarm alarm) throws Exception;
	
	int updateDNS(Alarm alarm) throws Exception;
	
	int updateEigen(Alarm alarm) throws Exception;
	
	int updateAlarmInfo(Alarm alarm) throws Exception;
	
	int insertEmail(Alarm alarm) throws Exception;
	
	int insertIP(Alarm alarm) throws Exception;
	
	int insertDNS(Alarm alarm) throws Exception;
	
	int insertEigen(Alarm alarm) throws Exception;
	
	int insertAlarmInfo(Alarm alarm) throws Exception;
	
	int getInfoID() throws Exception;
	
	int deleteAlarmInfoById(int id) throws Exception;
	
	int deleteEmailById(int id) throws Exception;
	
	int deleteIPById(int id) throws Exception;
	
	int deleteDNSById(int id) throws Exception;
	
	int deleteEigenById(int id) throws Exception;
}
