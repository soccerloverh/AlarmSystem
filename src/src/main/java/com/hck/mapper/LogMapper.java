package com.hck.mapper;

import java.util.List;

import com.hck.model.Log;
import com.hck.model.SqlLimit;

public interface LogMapper {
	void log(Log log);
	
	List<Log> getLogs(SqlLimit limit);
	
	int getRowCount();
}
