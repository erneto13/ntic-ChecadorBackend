package com.erneto13.ntic.repository;

import com.erneto13.ntic.model.Course;
import com.erneto13.ntic.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByCourse(Course course);
    List<Schedule> findByDay(DayOfWeek day);
    List<Schedule> findByStartTimeGreaterThanEqualAndEndTimeLessThanEqual(LocalTime startTime, LocalTime endTime);
    List<Schedule> findByProfessorId(Long professorId);
}