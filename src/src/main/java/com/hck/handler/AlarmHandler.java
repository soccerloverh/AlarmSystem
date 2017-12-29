package com.hck.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hck.model.Alarm;
import com.hck.model.BlackDNS;
import com.hck.model.BlackIP;
import com.hck.model.EigenAlarm;
import com.hck.model.EmailAlarm;
import com.hck.model.PageInfo;
import com.hck.model.State;
import com.hck.service.AlarmService;

@Controller
public class AlarmHandler {
	@Autowired
	AlarmService alarmService;
	private static final int PAGESIZE = 2;
	
	@RequestMapping("delete/{type}&{id}")
	@ResponseBody
	public State delete(@PathVariable String type,@PathVariable int id,HttpServletRequest request) {
		State state = new State();
		if(alarmService.deleteAlarmById(type, id)) {
			state.setInfo("OK");
			state.setStatus(State.OK);
			state.setHref("AlarmConfigeration");
		}else {
			state.setInfo("删除失败");
			state.setStatus(State.ERRO);
			return state;
		}
		return state;
	}
	
	//新增入口
	public State insert(Alarm alarm,HttpServletRequest request) {
		State state = new State();
		try {
			if (alarmService.addAlarm(alarm)) {
				state.setInfo("OK");
				state.setStatus(State.OK);
				state.setHref("AlarmConfigeration");
			} else {
				state.setInfo("新增失败");
				state.setStatus(State.ERRO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			state.setInfo("新增失败");
			state.setStatus(State.ERRO);
			return state;
		}
		return state;
	}
	
	//更新入口
	public State update(Alarm alarm,HttpServletRequest request) {
		State state = new State();
		if (alarmService.updateAlarm(alarm)) {
			state.setInfo("OK");
			state.setStatus(State.OK);
			state.setHref("AlarmConfigeration");
		} else {
			state.setInfo("更新失败");
			state.setStatus(State.ERRO);
		}
		return state;
	}
	
	@RequestMapping(value = "updateAlarm/eigen")
	@ResponseBody
	public State updateEigen(@RequestBody EigenAlarm alarm,HttpServletRequest request) {
		if (alarm.getEigen_id() == 0) {
			return ((AlarmHandler)(AopContext.currentProxy())).insert(alarm,request);
		} else {
			return ((AlarmHandler)(AopContext.currentProxy())).update(alarm,request);
		}
	}

	@RequestMapping(value = "updateAlarm/dns")
	@ResponseBody
	public State updateDNS(@RequestBody BlackDNS alarm,HttpServletRequest request) {
		if (alarm.getBlackDNS_id() == 0) {
			return ((AlarmHandler)(AopContext.currentProxy())).insert(alarm,request);
		} else {
			return ((AlarmHandler)(AopContext.currentProxy())).update(alarm,request);
		}
		
	}

	@RequestMapping(value = "updateAlarm/ip")
	@ResponseBody
	public State updateIP(@RequestBody BlackIP alarm,HttpServletRequest request) {
		if (alarm.getBlackIP_id() == 0) { // 新增
			return ((AlarmHandler)(AopContext.currentProxy())).insert(alarm,request);
		} else {
			return ((AlarmHandler)(AopContext.currentProxy())).update(alarm,request);
		}
	}

	@RequestMapping(value = "updateAlarm/email")
	@ResponseBody
	public State updateEmail(@RequestBody EmailAlarm alarm,HttpServletRequest request) {
		if (alarm.getEmail_id() == 0) { // 新增
			return ((AlarmHandler)(AopContext.currentProxy())).insert(alarm,request);
		} else { // 更新
			return ((AlarmHandler)(AopContext.currentProxy())).update(alarm,request);
		}
	}
	
	
	//访问警报配置页面
	@RequestMapping("AlarmConfigeration")
	public String toAlarmConfigerPage(HttpServletRequest request) {
		return "AlarmConfigeration";
	}

	//访问IP页面
	@RequestMapping("getEmailListByPage/{pageNow}")
	@ResponseBody
	public List<Alarm> getEmailListByPage(@PathVariable int pageNow) {
		PageInfo info = new PageInfo();
		info.setPageNow(pageNow);
		info.setPageSize(PAGESIZE);
		return alarmService.getAlarmsByPageInfoAndTypeId(info, AlarmService.EMAIL);
	}
	//访问IP页面
	@RequestMapping("getIPListByPage/{pageNow}")
	@ResponseBody
	public List<Alarm> getIPListByPage(@PathVariable int pageNow) {
		PageInfo info = new PageInfo();
		info.setPageNow(pageNow);
		info.setPageSize(PAGESIZE);
		return alarmService.getAlarmsByPageInfoAndTypeId(info, AlarmService.BLACKIP);
	}
	
	//访问DNS页面
	@RequestMapping("getDNSListByPage/{pageNow}")
	@ResponseBody
	public List<Alarm> getDNSListByPage(@PathVariable int pageNow) {
		PageInfo info = new PageInfo();
		info.setPageNow(pageNow);
		info.setPageSize(PAGESIZE);
		return alarmService.getAlarmsByPageInfoAndTypeId(info, AlarmService.BLACKDNS);
	}

	//访问Eigen页面
	@RequestMapping("getEigenListByPage/{pageNow}")
	@ResponseBody
	public List<Alarm> getEigenListByPage(@PathVariable int pageNow) {
		PageInfo info = new PageInfo();
		info.setPageNow(pageNow);
		info.setPageSize(PAGESIZE);
		return alarmService.getAlarmsByPageInfoAndTypeId(info, AlarmService.EIGEN);
	}

	//获取前端所以的警报分类
	@RequestMapping("getType")
	@ResponseBody
	public Map<Integer, String> getAlarmType() {
		Map<Integer, String> typeMap;
		typeMap = alarmService.getAlarmType();
		return typeMap;
	}

	//得到Email分页信息
	@RequestMapping("getEmailPageInfo")
	@ResponseBody
	public PageInfo getEmailPageInfo() {
		int count = alarmService.getAlarmCountByType(AlarmService.EMAIL);
		PageInfo info = new PageInfo();
		info.setRowCount(count);
		info.setPageSize(PAGESIZE);
		return info;
	}

	//得到IP分页信息
	@RequestMapping("getIPPageInfo")
	@ResponseBody
	public PageInfo getIPPageInfo() {
		int count = alarmService.getAlarmCountByType(AlarmService.BLACKIP);
		PageInfo info = new PageInfo();
		info.setRowCount(count);
		info.setPageSize(PAGESIZE);
		return info;
	}
	
	//得到DNS分页信息
	@RequestMapping("getDNSPageInfo")
	@ResponseBody
	public PageInfo getDNSPageInfo() {
		int count = alarmService.getAlarmCountByType(AlarmService.BLACKDNS);
		PageInfo info = new PageInfo();
		info.setRowCount(count);
		info.setPageSize(PAGESIZE);
		return info;
	}
	
	//得到Eigen分页信息
	@RequestMapping("getEigenPageInfo")
	@ResponseBody
	public PageInfo getEigenPageInfo() {
		int count = alarmService.getAlarmCountByType(AlarmService.EIGEN);
		PageInfo info = new PageInfo();
		info.setRowCount(count);
		info.setPageSize(PAGESIZE);
		return info;
	}

}
