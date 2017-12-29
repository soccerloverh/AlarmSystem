package com.hck.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hck.mapper.LogMapper;
import com.hck.model.Log;
import com.hck.model.PageInfo;
import com.hck.model.SqlLimit;
@Service
public class LogServiceImpl implements LogService {
	@Autowired
	LogMapper mapper;
	
	public int getRowCount() {
		return mapper.getRowCount();
	}
	
	@Override
	public List<Log> getLogByPageInfo(PageInfo info) {
		SqlLimit limit = getSqlLimitByPageInfo(info);
		return mapper.getLogs(limit);
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
}
