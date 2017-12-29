package com.hck.service;

import java.util.List;

import com.hck.model.Log;
import com.hck.model.PageInfo;

public interface LogService {
	List<Log> getLogByPageInfo(PageInfo info);
	int getRowCount();
}
