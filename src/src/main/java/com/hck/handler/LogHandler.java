package com.hck.handler;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hck.model.Log;
import com.hck.model.PageInfo;
import com.hck.service.AlarmService;
import com.hck.service.LogService;

@Controller
public class LogHandler {
	@Autowired
	LogService service;
	private static final int PAGESIZE = 10;
	@RequestMapping("getLogByPage/{pageNow}")
	@ResponseBody
	public List<Log> getLogByPage(@PathVariable int pageNow){
		PageInfo info = new PageInfo();
		info.setPageNow(pageNow);
		info.setPageSize(PAGESIZE);
		return service.getLogByPageInfo(info);
	}
	@RequestMapping("logging")
	public String toLoggingPage(HttpServletRequest request) {
		return "logging";
	}
	
	@RequestMapping("getLogPageInfo")
	@ResponseBody
	public PageInfo getLogPageInfo() {
		int count = service.getRowCount();
		PageInfo info = new PageInfo();
		info.setRowCount(count);
		info.setPageSize(PAGESIZE);
		return info;
	}
}
