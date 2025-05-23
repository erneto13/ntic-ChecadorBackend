package com.erneto13.ntic.controller;

import com.erneto13.ntic.model.Attendance;
import com.erneto13.ntic.model.Course;
import com.erneto13.ntic.model.Professor;
import com.erneto13.ntic.model.User;
import com.erneto13.ntic.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    // Obtener todas las asistencias
    @GetMapping
    public ResponseEntity<List<Attendance>> getAllAttendances() {
        List<Attendance> attendances = attendanceService.getAllAttendances();
        return new ResponseEntity<>(attendances, HttpStatus.OK);
    }

    // Obtener asistencia por ID
    @GetMapping("/{id}")
    public ResponseEntity<Attendance> getAttendanceById(@PathVariable Long id) {
        Optional<Attendance> attendance = attendanceService.getAttendanceById(id);
        return attendance.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear nueva asistencia
    @PostMapping
    public ResponseEntity<Attendance> createAttendance(@RequestBody Attendance attendance) {
        Attendance savedAttendance = attendanceService.saveAttendance(attendance);
        return new ResponseEntity<>(savedAttendance, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attendance> updateAttendance(@PathVariable Long id, @RequestBody Attendance attendance) {
        if (!attendanceService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        attendance.setId(Math.toIntExact(id));
        Attendance updatedAttendance = attendanceService.saveAttendance(attendance);
        return new ResponseEntity<>(updatedAttendance, HttpStatus.OK);
    }

    // Eliminar asistencia
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        if (!attendanceService.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        attendanceService.deleteAttendance(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Consulta por curso
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Attendance>> getAttendancesByCourse(@PathVariable Long courseId) {
        Optional<Course> course = courseService.getCourseById(courseId);
        if (course.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Attendance> attendances = attendanceService.getAttendancesByCourse(course.get());
        return new ResponseEntity<>(attendances, HttpStatus.OK);
    }

    // Consulta por fecha
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Attendance>> getAttendancesByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Attendance> attendances = attendanceService.getAttendancesByDate(date);
        return new ResponseEntity<>(attendances, HttpStatus.OK);
    }

    // Consulta por rango de fechas
    @GetMapping("/date-range")
    public ResponseEntity<List<Attendance>> getAttendancesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Attendance> attendances = attendanceService.getAttendancesByDateRange(startDate, endDate);
        return new ResponseEntity<>(attendances, HttpStatus.OK);
    }

    // Consulta por supervisor
    @GetMapping("/supervisor/{supervisorId}")
    public ResponseEntity<List<Attendance>> getAttendancesBySupervisor(@PathVariable Integer supervisorId) {
        Optional<User> supervisor = userService.getUserById(supervisorId);
        if (supervisor.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Attendance> attendances = attendanceService.getAttendancesBySupervisor(supervisor.get());
        return new ResponseEntity<>(attendances, HttpStatus.OK);
    }

    // Consulta por tema semanal
    @GetMapping("/weekly-topic")
    public ResponseEntity<List<Attendance>> getAttendancesByWeeklyTopic(@RequestParam String topic) {
        List<Attendance> attendances = attendanceService.getAttendancesByWeeklyTopic(topic);
        return new ResponseEntity<>(attendances, HttpStatus.OK);
    }

    // Calcular porcentaje de asistencia
    @GetMapping("/percentage/{professorId}")
    public ResponseEntity<Double> calculateAttendancePercentage(
            @PathVariable Long professorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Professor professor = professorService.getProfessorById(professorId);
        if (professor == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        double percentage = attendanceService.calculateAttendancePercentage(professor, startDate, endDate);
        return new ResponseEntity<>(percentage, HttpStatus.OK);
    }
}