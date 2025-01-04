package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dao.GenericDao;
import com.example.app.dao.ScheduleDao;
import com.example.app.domain.Schedule;

@Service
public class ScheduleServiceImpl extends GenericService<Schedule> implements ScheduleService {
	
	@Autowired
	ScheduleDao scheduleDao;
	
	public ScheduleServiceImpl(ScheduleDao scheduleDao) {
		this.scheduleDao = scheduleDao;
	}
	
	@Override
	protected GenericDao<Schedule> getDao() {
		return scheduleDao;
	}
	
	@Override
	public List<Schedule> getScheduleList() throws Exception {
		return scheduleDao.selectAll();
	}
	
	@Override
	public List<Schedule> getCurrentMonthList() throws Exception {
		return scheduleDao.selectCurrentMonthList();
	}

	@Override
	public Schedule getScheduleById(Integer id) throws Exception {
		return scheduleDao.selectById(id);
	}

	@Override
	public void addSchedule(Schedule schedule) throws Exception {
		scheduleDao.insert(schedule);
	}

	@Override
	public void editSchedule(Schedule schedule) throws Exception {
		scheduleDao.update(schedule);
	}

	@Override
	public void deleteSchedule(Schedule schedule) throws Exception {
		scheduleDao.delete(schedule);
	}

}
