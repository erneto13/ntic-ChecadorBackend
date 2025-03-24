package com.erneto13.ntic.service;

import com.erneto13.ntic.model.Attendance;
import com.erneto13.ntic.model.Course;
import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.model.User;
import com.erneto13.ntic.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    public Optional<Attendance> getAttendanceById(Long id) {
        return attendanceRepository.findById(id);
    }

    public Attendance saveAttendance(Attendance attendance) {
        return attendanceRepository.save(attendance);
    }

    public Attendance updateAttendance(Long id, Attendance updatedAttendance) {
        if (attendanceRepository.existsById(id)) {
            updatedAttendance.setId(Math.toIntExact(id));
            return attendanceRepository.save(updatedAttendance);
        }
        return null;
    }

    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return attendanceRepository.existsById(id);
    }

    public List<Attendance> getAttendancesByProfessor(Professor professor) {
        return attendanceRepository.findByProfessor(professor);
    }

    public List<Attendance> getAttendancesByCourse(Course course) {
        return attendanceRepository.findByCourse(course);
    }

    public List<Attendance> getAttendancesByDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }

    public List<Attendance> getAttendancesByDateRange(LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findByDateBetween(startDate, endDate);
    }

    public List<Attendance> getAttendancesBySupervisor(User supervisor) {
        return attendanceRepository.findBySupervisor(supervisor);
    }

    public List<Attendance> getAttendancesByWeeklyTopic(String weeklyTopic) {
        return attendanceRepository.findByWeeklyTopicContaining(weeklyTopic);
    }

    public long countAttendancesByProfessorAndDateRange(Professor professor, LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.countByProfessorAndDateBetweenAndPresentTrue(professor, startDate, endDate);
    }

    public double calculateAttendancePercentage(Professor professor, LocalDate startDate, LocalDate endDate) {
        long totalAttendances = attendanceRepository.countByProfessorAndDateBetween(professor, startDate, endDate);
        long presentAttendances = attendanceRepository.countByProfessorAndDateBetweenAndPresentTrue(professor, startDate, endDate);

        if (totalAttendances == 0) {
            return 0.0;
        }

        return (double) presentAttendances / totalAttendances * 100.0;
    }
}