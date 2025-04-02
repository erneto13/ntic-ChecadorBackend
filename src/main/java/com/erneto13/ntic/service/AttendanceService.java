package com.erneto13.ntic.service;

import com.erneto13.ntic.dto.AttendanceCheckDto;
import com.erneto13.ntic.dto.ResponseDto;
import com.erneto13.ntic.model.Attendance;
import com.erneto13.ntic.model.Course;
import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.model.User;
import com.erneto13.ntic.repository.AttendanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(AttendanceService.class);

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

    public List<Attendance> getAttendancesByChecker(User checker) {
        return attendanceRepository.findByChecker(checker);
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
    public ResponseDto getAttendanceToday(Long professorId, Long courseId) {
        String formattedDate = LocalDate.now().toString();
        String query = "SELECT COUNT(*) FROM attendances WHERE professor_id = ? AND course_id = ? AND date = ?";

        logger.info("Checking attendance for professorId: {}, courseId: {}, date: {}", professorId, courseId, formattedDate);

        try {
            Integer attendanceCount = jdbcTemplate.queryForObject(
                    query,
                    Integer.class,
                    professorId,
                    courseId,
                    formattedDate
            );
            logger.info("Attendance count: {}", attendanceCount);

           return attendanceCount > 0
                    ? new ResponseDto(true, "Asistencia registrada")
                    : new ResponseDto(false, "No existe asistencia");
        } catch (EmptyResultDataAccessException e) {
            logger.warn("No attendance found: {}", e.getMessage());
            return new ResponseDto(404, "No existe asistencia");
        } catch (Exception e) {
            logger.error("Error checking attendance: {}", e.getMessage(), e);
            return new ResponseDto(500, "Error al verificar asistencia");
        }
    }
}