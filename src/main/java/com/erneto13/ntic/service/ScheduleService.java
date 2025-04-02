package com.erneto13.ntic.service;

import com.erneto13.ntic.model.Course;
import com.erneto13.ntic.model.Schedule;
import com.erneto13.ntic.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public Optional<Schedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    public List<Schedule> getSchedulesByCourse(Course course) {
        return scheduleRepository.findByCourse(course);
    }

    public List<Schedule> getSchedulesByDay(String day) {
        return scheduleRepository.findByDay(day);
    }

    public List<Schedule> getSchedulesByTimeRange(String startTime, String endTime) {
        return scheduleRepository.findByStartTimeGreaterThanEqualAndEndTimeLessThanEqual(startTime, endTime);
    }

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
}