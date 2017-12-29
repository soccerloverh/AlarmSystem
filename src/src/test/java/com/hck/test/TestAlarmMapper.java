package com.hck.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hck.mapper.AlarmMapper;
import com.hck.model.EmailAlarm;
import com.hck.model.SqlLimit;
import com.hck.service.AlarmService;
import com.hck.service.AlarmServiceImpl;

public class TestAlarmMapper {
private ApplicationContext context;
	
	@Before
	public void init() {
		context = new ClassPathXmlApplicationContext("spring/ApplicationContext.xml");
	}
	
	@Test
	public void testGetEmails() throws Exception {
		AlarmMapper mapper = context.getBean(AlarmMapper.class);
		SqlLimit limit = new SqlLimit(0, 2);
		System.out.println(mapper.getEmailAlarmWithLimit(limit));
	}
	
	@Test
	public void testUpdateEmail() throws Exception{
		AlarmService service = context.getBean(AlarmService.class);
		AlarmMapper mapper = context.getBean(AlarmMapper.class);
		EmailAlarm alarm = null;
		SqlLimit limit = new SqlLimit(0, 1);
		alarm = (EmailAlarm) mapper.getEmailAlarmWithLimit(limit).get(0);
		System.out.println(alarm);
//		alarm.getInfo().setAlarm_name("12th AAA");
		alarm.setEmail_blockwords("12th Blockwords");
		service.updateAlarm(alarm);
	}
}
