package com.erneto13.ntic.controller;

import com.erneto13.ntic.model.Course;
import com.erneto13.ntic.model.Schedule;
import com.erneto13.ntic.service.CourseService;
import com.erneto13.ntic.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private CourseService courseService;

    // Obtener todos los horarios
    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    // Obtener horario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
        Optional<Schedule> schedule = scheduleService.getScheduleById(id);
        return schedule.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear nuevo horario
    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        Schedule savedSchedule = scheduleService.saveSchedule(schedule);
        return new ResponseEntity<>(savedSchedule, HttpStatus.CREATED);
    }

    // Actualizar horario existente
    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @RequestBody Schedule schedule) {
        if (!scheduleService.getScheduleById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        schedule.setId(Math.toIntExact(id));
        Schedule updatedSchedule = scheduleService.saveSchedule(schedule);
        return new ResponseEntity<>(updatedSchedule, HttpStatus.OK);
    }

    // Eliminar horario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        if (!scheduleService.getScheduleById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Obtener horarios por curso
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Schedule>> getSchedulesByCourse(@PathVariable Long courseId) {
        Optional<Course> course = courseService.getCourseById(courseId);
        if (course.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Schedule> schedules = scheduleService.getSchedulesByCourse(course.get());
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }

    // Obtener horarios por día de la semana
    @GetMapping("/day/{day}")
    public ResponseEntity<List<Schedule>> getSchedulesByDay(@PathVariable DayOfWeek day) {
        List<Schedule> schedules = scheduleService.getSchedulesByDay(day);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }
    @GetMapping("/professor/{professorId}")
    public ResponseEntity<List<Schedule>> getSchedulesByProfessor(@PathVariable Long professorId) {
        List<Schedule> schedules = scheduleService.getSchedulesByProfessorId(professorId);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }
    // Obtener horarios por rango de tiempo
    @GetMapping("/time-range")
    public ResponseEntity<List<Schedule>> getSchedulesByTimeRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {
        List<Schedule> schedules = scheduleService.getSchedulesByTimeRange(startTime, endTime);
        return new ResponseEntity<>(schedules, HttpStatus.OK);
    }
}