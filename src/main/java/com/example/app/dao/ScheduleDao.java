package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Schedule;

@Mapper
public interface ScheduleDao extends GenericDao<Schedule> {
	public List<Schedule> selectAll() throws Exception;
	public List<Schedule> selectCurrentMonthList() throws Exception;
	public List<Schedule> selectByEventList(String dateStr) throws Exception;
	public Schedule selectById(Integer id) throws Exception;
	public void insert(Schedule schedule) throws Exception;
	public void update(Schedule schedule) throws Exception;
	public void delete(Schedule schedule) throws Exception;
}
