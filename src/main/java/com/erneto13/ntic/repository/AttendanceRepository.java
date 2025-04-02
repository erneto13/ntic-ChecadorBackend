package com.erneto13.ntic.repository;

import com.erneto13.ntic.model.Attendance;
import com.erneto13.ntic.model.Course;
import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByProfessor(Professor professor);
    List<Attendance> findByCourse(Course course);
    List<Attendance> findByDate(LocalDate date);
    List<Attendance> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Attendance> findByWeeklyTopicContaining(String weeklyTopic);

    long countByProfessorAndDateBetween(Professor professor, LocalDate startDate, LocalDate endDate);
    long countByProfessorAndDateBetweenAndPresentTrue(Professor professor, LocalDate startDate, LocalDate endDate);

    List<Attendance> findByChecker(User checker);
    Attendance findByProfessorAndCourseAndDate(Professor professor, Course course, LocalDate date);
}