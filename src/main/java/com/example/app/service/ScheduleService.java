package com.example.app.service;

import java.util.List;

import com.example.app.domain.Schedule;

public interface ScheduleService {
	public List<Schedule>getScheduleList() throws Exception;
	public List<Schedule>getCurrentMonthList() throws Exception;
	public Schedule getScheduleById(Integer id) throws Exception;
	public void addSchedule(Schedule schedule) throws Exception;
	public void editSchedule(Schedule schedule) throws Exception;
	public void deleteSchedule(Schedule schedule) throws Exception;
	public int getTotalPages(int numPerPage) throws Exception;
	public List<Schedule> getListByPage(int page, int numPerPage) throws Exception;
}
